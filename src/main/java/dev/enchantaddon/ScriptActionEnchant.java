
package dev.enchantaddon;

import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.api.IScriptActionProvider;
import net.eq2online.macros.scripting.api.IMacro;
import net.eq2online.macros.scripting.api.IMacroAction;
import net.eq2online.macros.scripting.api.IReturnValue;

import net.eq2online.macros.scripting.api.APIVersion;

@APIVersion(18)
public class ScriptActionEnchant extends ScriptAction {

    public ScriptActionEnchant() {
        this(ScriptContext.MAIN);
    }

    public ScriptActionEnchant(final ScriptContext context) {
        super(context, "enchant");
    }

    private static final String MINECRAFT_CLASS = "net.minecraft.client.Minecraft";
    private static final String MINECRAFT_CLASS_GET_FUNC = "func_71410_x";

    private static final String PLAYER_CONTROLLER_CLASS = "net.minecraft.client.multiplayer.PlayerControllerMP";

    private static final String ENCHANTMENT_CONTAINER_CLASS = "net.minecraft.inventory.ContainerEnchantment";

    @Override
    public IReturnValue execute(
            IScriptActionProvider provider,
            IMacro macro,
            IMacroAction action,
            String rawParams,
            String[] params) {

        try {
            int option = parseOption(params);

            Class<?> minecraftClass = Class.forName(MINECRAFT_CLASS);

            Object minecraft = minecraftClass
                    .getMethod(MINECRAFT_CLASS_GET_FUNC)
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
                    ENCHANTMENT_CONTAINER_CLASS)) {
                provider.actionAddChatMessage("Mesa de encantamento não está aberta");
                return null;
            }

            // Get window ID
            java.lang.reflect.Field windowIdField = container.getClass().getField("field_75152_c");

            int windowId = windowIdField.getInt(container);

            // Get controller
            java.lang.reflect.Field controllerField = minecraftClass.getDeclaredField("field_71442_b");
            controllerField.setAccessible(true);

            Object controller = controllerField.get(minecraft);

            // Invoke enchantment selection
            Class<?> controllerClass = Class.forName(PLAYER_CONTROLLER_CLASS);

            java.lang.reflect.Method enchantMethod = controllerClass.getDeclaredMethod(
                    "func_78756_a",
                    int.class,
                    int.class);

            enchantMethod.setAccessible(true);
            enchantMethod.invoke(controller, windowId, option - 1);

            provider.actionAddChatMessage("Encantamento: " + option);

        } catch (Exception e) {
            provider.actionAddChatMessage("Error: " + e);
        }

        return null;
    }

    @Override
    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }

    private static int parseOption(String[] params) throws IllegalArgumentException {
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException(
                    "Faltando parametro de encantamento, deve ser 1, 2 ou 3.");
        }

        int option = Integer.parseInt(params[0].trim());

        if (option < 1 || option > 3) {
            throw new IllegalArgumentException(
                    "Opcao de encantamento invalida: " + option);
        }

        return option;
    }
}
