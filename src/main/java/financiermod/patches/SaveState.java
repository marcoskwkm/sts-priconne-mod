package financiermod.patches;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import basemod.abstracts.CustomSavable;
import financiermod.ui.SkinSelectScreen;

public class SaveState implements CustomSavable<SaveState.SaveData> {
    public class SaveData {
        public int characterIndex;
        public int skinIndex;
        public boolean disabled;
        public SaveData(int characterIndex, int skinIndex, boolean disabled) {
            this.characterIndex = characterIndex;
            this.skinIndex = skinIndex;
            this.disabled = disabled;
        }
    };
    
    public static final String SAVE_ID = "priconne_skin";
    
    public SaveState() {
    }
    
    @Override
    public SaveData onSave() {
        return new SaveData(SkinSelectScreen.Inst.characterIndex, SkinSelectScreen.Inst.skinIndex, SkinSelectScreen.Inst.disabled);
    }
    
    public void onLoad(SaveData data) {
    }

    @Override
    public Type savedType() {
        return new TypeToken<SaveData>(){}.getType();
    }
}