package org.marj4n.irons_spellbooks.client.hud;
import net.minecraft.client.gui.GuiGraphics;
public final class NativeTooltip {
    public static void renderBackground(GuiGraphics graphics,int x,int y,int width,int height,int z,
            int bgTop,int bgBottom,int edgeTop,int edgeBottom) {
        x-=3; y-=3; width+=6; height+=6;
        graphics.fillGradient(x,y-1,x+width,y,z,bgTop,bgTop);
        graphics.fillGradient(x,y+height,x+width,y+height+1,z,bgBottom,bgBottom);
        graphics.fillGradient(x,y,x+width,y+height,z,bgTop,bgBottom);
        graphics.fillGradient(x-1,y,x,y+height,z,bgTop,bgBottom);
        graphics.fillGradient(x+width,y,x+width+1,y+height,z,bgTop,bgBottom);
        graphics.fillGradient(x,y+1,x+1,y+height-1,z,edgeTop,edgeBottom);
        graphics.fillGradient(x+width-1,y+1,x+width,y+height-1,z,edgeTop,edgeBottom);
        graphics.fillGradient(x,y,x+width,y+1,z,edgeTop,edgeTop);
        graphics.fillGradient(x,y+height-1,x+width,y+height,z,edgeBottom,edgeBottom);
    }
}
