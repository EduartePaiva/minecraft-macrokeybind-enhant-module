
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

    @Override
    public IReturnValue execute(
            IScriptActionProvider provider,
            IMacro macro,
            IMacroAction action,
            String rawParams,
            String[] params) {
        try {
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");

            provider.actionAddChatMessage(
                    "Minecraft class found: " + minecraftClass.getName());
        } catch (Exception e) {
            provider.actionAddChatMessage(
                    "Minecraft access failed: " + e.getClass().getName());
        }

        if (params.length != 1) {
            provider.actionAddChatMessage("Uso: enchant(1), enchant(2) ou enchant(3)");
            return null;
        }

        int level;

        try {
            level = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            provider.actionAddChatMessage("Nivel invalido!");
            return null;
        }

        if (level < 1 || level > 3) {
            provider.actionAddChatMessage("Nivel deve ser 1, 2 ou 3!");
            return null;
        }

        provider.actionAddChatMessage("Nivel selecionado: " + level);

        return null;
    }

    @Override
    public void onInit() {
        this.context.getCore().registerScriptAction(this);
    }
}
