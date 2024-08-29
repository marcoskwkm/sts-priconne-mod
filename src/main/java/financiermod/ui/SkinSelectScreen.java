// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.ui;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import financiermod.FinancierMod;

interface Func {
    void apply();
}

class SkinMetadata {
    public String charPath;
    public String name;

    public SkinMetadata(String name, String charPath) {
        this.name = name;
        this.charPath = charPath;
    }
}


public class SkinSelectScreen implements ISubscriber {
    public static class CharacterSkins {
        public String name;
        public Skin[] skins;

        public CharacterSkins(String character, String shoulderPath, SkinMetadata[] skins) {
            this.name = character;
            this.skins = new Skin[skins.length];
            for (int i = 0; i < skins.length; i++) {
                this.skins[i] = new Skin(skins[i].name, shoulderPath, skins[i].charPath);
            }
        }
    }

    public static class Skin {
        public String charPath;
        public String shoulder;
        public String name;
        
        public Skin(String name, String shoulderPath, String charPath) {
            this.charPath = charPath;
            this.shoulder = shoulderPath;
            this.name = name;
        }
    }

    public static class CharacterNames {
        public static final String pecorine = "Pecorine";
        public static final String kyaryl = "Kyaryl";
        public static final String kokkoro = "Kokkoro";
    }

    private static final CharacterSkins[] characters;
    public static SkinSelectScreen Inst;
    public Hitbox charLeftHb;
    public Hitbox charRightHb;
    public Hitbox skinLeftHb;
    public Hitbox skinRightHb;
    public Hitbox disabledHb;
    public TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationStateData stateData;
    public AnimationState state;
    public String curName = "";
    public int characterIndex = 0;
    public int skinIndex = 0;
    public boolean disabled = false;
    
    public static CharacterSkins getCharacter() {
        return characters[Inst.characterIndex];
    }
    public static Skin getSkin() {
        return SkinSelectScreen.getCharacter().skins[Inst.skinIndex];
    }
    public static Skin getSkin(int characterIndex, int skinIndex) {
        return characters[characterIndex].skins[skinIndex];
    }
    
    public SkinSelectScreen() {
        this.refresh();
        this.charLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.charRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.skinLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.skinRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.disabledHb = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
        BaseMod.subscribe(this);
    }
    
    public void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTimeScale(1.2F);
    }
    
    public void refresh() {
        Skin currentSkin = characters[this.characterIndex].skins[this.skinIndex];
        this.curName = currentSkin.name;
        this.loadAnimation(currentSkin.charPath + ".atlas", currentSkin.charPath + ".json", 1.5F);
    }

    public int prevCharIndex() {
        return this.characterIndex - 1 < 0 ? characters.length - 1 : this.characterIndex - 1;
    }

    public int nextCharIndex() {
        return this.characterIndex + 1 > characters.length - 1 ? 0 : this.characterIndex + 1;
    }
    
    public int prevSkinIndex() {
        return this.skinIndex - 1 < 0 ? characters[this.characterIndex].skins.length - 1 : this.skinIndex - 1;
    }
    
    public int nextSkinIndex() {
        return this.skinIndex + 1 > characters[this.characterIndex].skins.length - 1 ? 0 : this.skinIndex + 1;
    }
    
    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float charCenterY = (float)Settings.HEIGHT * 0.5F - 20.0F * Settings.scale;
        float skinCenterY = charCenterY - 50.0F * Settings.scale;
        float disabledCenterY = skinCenterY - 50.0F * Settings.scale;
        this.charLeftHb.move(centerX - 200.0F * Settings.scale, charCenterY);
        this.charRightHb.move(centerX + 200.0F * Settings.scale, charCenterY);
        this.skinLeftHb.move(centerX - 200.0F * Settings.scale, skinCenterY);
        this.skinRightHb.move(centerX + 200.0F * Settings.scale, skinCenterY);
        this.disabledHb.move(centerX - 125.0F * Settings.scale, disabledCenterY);
        this.updateInputs();
    }
    
    private void updateInputs() {
        this.updateInput(charLeftHb, () -> {
            this.characterIndex = this.prevCharIndex();
            this.skinIndex = Math.min(this.skinIndex, characters[this.characterIndex].skins.length - 1);
            this.refresh();
        });
        this.updateInput(charRightHb, () -> {
            this.characterIndex = this.nextCharIndex();
            this.skinIndex = Math.min(this.skinIndex, characters[this.characterIndex].skins.length - 1);
            this.refresh();
        });
        this.updateInput(skinLeftHb, () -> { 
            this.skinIndex = this.prevSkinIndex();
            this.refresh();
        });
        this.updateInput(skinRightHb, () -> {
            this.skinIndex = this.nextSkinIndex();
            this.refresh();
        });
        this.updateInput(disabledHb, () -> { this.disabled = !this.disabled; });
    }
    
    private void updateInput(Hitbox hb, Func f) {
        hb.update();
        if (hb.clicked) {
            hb.clicked = false;
            CardCrawlGame.sound.play("UI_CLICK_1");
            f.apply();
        }
        if (InputHelper.justClickedLeft && hb.hovered) {
            hb.clickStarted = true;
        }
    }
    
    public void render(SpriteBatch sb) {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.renderSkin(sb, centerX, centerY);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Skin select", centerX, centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
        Color goldColorAdjusted = Settings.GOLD_COLOR.cpy();
        goldColorAdjusted.a /= 2.0F;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, characters[this.characterIndex].name, centerX, centerY - 20.0F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, characters[this.characterIndex].skins[this.skinIndex].name, centerX, centerY - 70.0F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Disable skin", centerX, centerY - 120.0F * Settings.scale, Settings.GOLD_COLOR);
        this.renderHitbox(sb, charLeftHb, ImageMaster.CF_LEFT_ARROW, 48);
        this.renderHitbox(sb, charRightHb, ImageMaster.CF_RIGHT_ARROW, 48);
        this.renderHitbox(sb, skinLeftHb, ImageMaster.CF_LEFT_ARROW, 48);
        this.renderHitbox(sb, skinRightHb, ImageMaster.CF_RIGHT_ARROW, 48);
        this.renderHitbox(sb, disabledHb, ImageMaster.CHECKBOX, 72);
        if (this.disabled) {
            this.renderHitbox(sb, disabledHb, ImageMaster.TICK, 72);
        }
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if (this.atlas != null) {
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(x, y);
            sb.end();
            CardCrawlGame.psb.begin();
            AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        }
    }

    public void renderHitbox(SpriteBatch sb, Hitbox hb, Texture image, int size) {
        if (hb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        float halfSize = (float)(size) / 2;
        sb.draw(image, hb.cX - halfSize, hb.cY - halfSize, /*halfSize*/ size, /*halfSize*/ size, size, size, Settings.scale, Settings.scale, 0.0F, 0, 0, size, size, false, false);
        hb.render(sb);
    }

    static {
        SkinMetadata[] pecorineSkins = {
            new SkinMetadata("Default", FinancierMod.imagePath("pecorine/char/105861")),
            new SkinMetadata("Summer", FinancierMod.imagePath("pecorine/char/107561")),
            new SkinMetadata("New Year", FinancierMod.imagePath("pecorine/char/111831")),
            new SkinMetadata("Overload", FinancierMod.imagePath("pecorine/char/121031")),
            new SkinMetadata("Christmas", FinancierMod.imagePath("pecorine/char/127931")),
            new SkinMetadata("Princess", FinancierMod.imagePath("pecorine/char/180431"))
        };
        SkinMetadata[] kyarylSkils = {
            new SkinMetadata("Default", FinancierMod.imagePath("kyaryl/char/106061")),
            new SkinMetadata("Summer", FinancierMod.imagePath("kyaryl/char/107861")),
            new SkinMetadata("New Year", FinancierMod.imagePath("kyaryl/char/112031")),
            new SkinMetadata("Overload", FinancierMod.imagePath("kyaryl/char/121131")),
            new SkinMetadata("Transfer Student", FinancierMod.imagePath("kyaryl/char/127231")),
            new SkinMetadata("Princess", FinancierMod.imagePath("kyaryl/char/180631"))
        };
        SkinMetadata[] kokkoroSkins = {
            new SkinMetadata("Default", FinancierMod.imagePath("kokkoro/char/105961")),
            new SkinMetadata("Summer", FinancierMod.imagePath("kokkoro/char/107661")),
            new SkinMetadata("New Year", FinancierMod.imagePath("kokkoro/char/111931")),
            new SkinMetadata("Ceremonial Dress", FinancierMod.imagePath("kokkoro/char/115531")),
            new SkinMetadata("Ranger", FinancierMod.imagePath("kokkoro/char/125331")),
            new SkinMetadata("Princess", FinancierMod.imagePath("kokkoro/char/180531"))
        };
        characters = new CharacterSkins[] {
            new CharacterSkins(CharacterNames.pecorine, FinancierMod.imagePath("pecorine/shoulder.png"), pecorineSkins),
            new CharacterSkins(CharacterNames.kyaryl, FinancierMod.imagePath("kyaryl/shoulder.png"), kyarylSkils),
            new CharacterSkins(CharacterNames.kokkoro, FinancierMod.imagePath("kokkoro/shoulder.png"), kokkoroSkins),
        };

        Inst = new SkinSelectScreen();
    }
}
