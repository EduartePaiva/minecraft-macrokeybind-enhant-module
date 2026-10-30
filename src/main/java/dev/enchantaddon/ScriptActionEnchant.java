
package dev.enchantaddon;

import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import net.eq2online.macros.scripting.api.APIVersion;

@APIVersion(18)
public class ScriptActionEnchant extends ScriptAction {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("EnchantAddon");

    static {
        try {
            FileHandler handler = new FileHandler("enchant-addon.log", true);
            handler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(handler);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ScriptActionEnchant() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionEnchant(final ScriptContext context) {
        super(context, "enchant");
    }

    // net.minecraft.client.Minecraft
    // func_71410_x

    @Override
    public IReturnValue execute(
            IScriptActionProvider provider,
            IMacro macro,
            IMacroAction action,
            String rawParams,
            String[] params) {

        try {
            int option = Integer.parseInt(params[0]);

            if (option < 1 || option > 3) {
                provider.actionAddChatMessage("Opção inválida de encantamento: " + option);
                return null;
            }

            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");

            Object minecraft = minecraftClass
                    .getMethod("func_71410_x")
                    .invoke(null);

            // Get player
            java.lang.reflect.Field playerField = minecraftClass.getDeclaredField("field_71439_g");
            playerField.setAccessible(true);

            Object player = playerField.get(minecraft);

            // Get currently open container
            java.lang.reflect.Field containerField = player.getClass().getField("field_71070_bA");

            Object container = containerField.get(player);

            // Ensure an enchantment table is open
            if (!container.getClass().getName().equals(
                    "net.minecraft.inventory.ContainerEnchantment")) {
                provider.actionAddChatMessage("Mesa de encantamento não está aberta");
                return null;
            }

            // Get window ID
            java.lang.reflect.Field windowIdField = container.getClass().getField("field_75152_c");

            int windowId = windowIdField.getInt(container);

            provider.actionAddChatMessage("windowId: " + windowId);

            // Get controller
            java.lang.reflect.Field controllerField = minecraftClass.getDeclaredField("field_71442_b");
            controllerField.setAccessible(true);

            Object controller = controllerField.get(minecraft);

            // Invoke enchantment selection
            Class<?> controllerClass = Class.forName(
                    "net.minecraft.client.multiplayer.PlayerControllerMP");

            java.lang.reflect.Method enchantMethod = controllerClass.getDeclaredMethod(
                    "func_78756_a",
                    int.class,
                    int.class);

            enchantMethod.setAccessible(true);
            enchantMethod.invoke(controller, windowId, option - 1);

            provider.actionAddChatMessage("Encantamento: " + option);

        } catch (Exception e) {
            provider.actionAddChatMessage("Enchantment error: " + e);
        }

        return null;
    }

    @Override
    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
