// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.ui;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
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
import java.util.ArrayList;

import financiermod.FinancierMod;

public class SkinSelectScreen implements ISubscriber, CustomSavable<Integer> {
    public static class Skin {
        public String charPath;
        public String shoulder;
        public String name;
        
        public Skin(int index, String charPath) {
            this.charPath = charPath;
            this.shoulder = FinancierMod.imagePath("kokkoro/shoulder.png");
            this.name = SkinSelectScreen.TEXT[index + 1];
        }
    }

    public interface Func {
        void apply();
    }

    private static final String[] TEXT = new String[]{"Skin", "Default", "Summer", "New Year", "Ceremonial Dress", "Ranger", "Princess"};
    private static final ArrayList<Skin> SKINS = new ArrayList<Skin>();
    public static SkinSelectScreen Inst;
    public Hitbox charLeftHb;
    public Hitbox charRightHb;
    public Hitbox skinLeftHb;
    public Hitbox skinRightHb;
    public TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationStateData stateData;
    public AnimationState state;
    public String curName = "";
    public String nextName = "";
    public int index = 0;
    
    public static Skin getSkin() {
        return (Skin)SKINS.get(Inst.index);
    }
    
    public SkinSelectScreen() {
        this.refresh();
        this.charLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.charRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.skinLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.skinRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        BaseMod.subscribe(this);
        BaseMod.addSaveField("Kokkoro_skin", this);
        BaseMod.addSaveField("Defect_skin", this);
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
        Skin currentSkin = (Skin)SKINS.get(this.index);
        this.curName = currentSkin.name;
        this.loadAnimation(currentSkin.charPath + ".atlas", currentSkin.charPath + ".json", 1.5F);
        this.nextName = ((Skin)SKINS.get(this.nextIndex())).name;
    }
    
    public int prevIndex() {
        return this.index - 1 < 0 ? SKINS.size() - 1 : this.index - 1;
    }
    
    public int nextIndex() {
        return this.index + 1 > SKINS.size() - 1 ? 0 : this.index + 1;
    }
    
    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float charCenterY = (float)Settings.HEIGHT * 0.5F;
        float skinCenterY = charCenterY - 50.0F * Settings.scale;
        this.charLeftHb.move(centerX - 200.0F * Settings.scale, charCenterY);
        this.charRightHb.move(centerX + 200.0F * Settings.scale, charCenterY);
        this.skinLeftHb.move(centerX - 200.0F * Settings.scale, skinCenterY);
        this.skinRightHb.move(centerX + 200.0F * Settings.scale, skinCenterY);
        this.updateInputs();
    }
    
    private void updateInputs() {
        this.updateInput(charLeftHb, () -> { this.index = this.prevIndex(); });
        this.updateInput(charRightHb, () -> { this.index = this.nextIndex(); });
        this.updateInput(skinLeftHb, () -> {});
        this.updateInput(skinRightHb, () -> {});
        // this.charLeftHb.update();
        // this.charRightHb.update();
        // this.skinLeftHb.update();
        // this.skinRightHb.update();
        // if (this.charLeftHb.clicked) {
        //     this.charLeftHb.clicked = false;
        //     CardCrawlGame.sound.play("UI_CLICK_1");
        //     this.index = this.prevIndex();
        //     this.refresh();
        // }

        // if (this.charRightHb.clicked) {
        //     this.charRightHb.clicked = false;
        //     CardCrawlGame.sound.play("UI_CLICK_1");
        //     this.index = this.nextIndex();
        //     this.refresh();
        // }

        // if (InputHelper.justClickedLeft) {
        //     if (this.charLeftHb.hovered) {
        //         this.charLeftHb.clickStarted = true;
        //     }
            
        //     if (this.charRightHb.hovered) {
        //         this.charRightHb.clickStarted = true;
        //     }
        // }
    }
    
    private void updateInput(Hitbox hb, Func f) {
        hb.update();
        if (hb.clicked) {
            hb.clicked = false;
            CardCrawlGame.sound.play("UI_CLICK_1");
            f.apply();
            this.refresh();
        }
        if (InputHelper.justClickedLeft && hb.hovered) {
            hb.clickStarted = true;
        }
    }
    
    public void render(SpriteBatch sb) {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.5F;
        this.renderSkin(sb, centerX, centerY);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[0], centerX, centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
        Color goldColorAdjusted = Settings.GOLD_COLOR.cpy();
        goldColorAdjusted.a /= 2.0F;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Skin name", centerX, centerY - 50.0F * Settings.scale, Settings.GOLD_COLOR);
        this.renderHitbox(sb, charLeftHb, ImageMaster.CF_LEFT_ARROW);
        this.renderHitbox(sb, charRightHb, ImageMaster.CF_RIGHT_ARROW);
        this.renderHitbox(sb, skinLeftHb, ImageMaster.CF_LEFT_ARROW);
        this.renderHitbox(sb, skinRightHb, ImageMaster.CF_RIGHT_ARROW);
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

    public void renderHitbox(SpriteBatch sb, Hitbox hb, Texture image) {
        if (hb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(image, hb.cX - 24.0F, hb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        hb.render(sb);
    }
    
    public void onLoad(Integer arg0) {
        this.index = arg0;
    }
    
    public Integer onSave() {
        return this.index;
    }
    
    static {
        SKINS.add(new Skin(0, FinancierMod.imagePath("kokkoro/char/105961")));
        SKINS.add(new Skin(1, FinancierMod.imagePath("kokkoro/char/107661")));
        SKINS.add(new Skin(2, FinancierMod.imagePath("kokkoro/char/111931")));
        SKINS.add(new Skin(3, FinancierMod.imagePath("kokkoro/char/115531")));
        SKINS.add(new Skin(4, FinancierMod.imagePath("kokkoro/char/125331")));
        SKINS.add(new Skin(5, FinancierMod.imagePath("kokkoro/char/180531")));
        Inst = new SkinSelectScreen();
    }
}
