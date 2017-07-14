package com.nbsaw.miaohu.captcha;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import java.awt.*;
import java.awt.image.BufferedImage;

class CustomCaptcha extends Configurable implements GimpyEngine
{
    public BufferedImage getDistortedImage(BufferedImage baseImage)
    {
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(),
                baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = (Graphics2D) distortedImage.getGraphics();
        RippleFilter rippleFilter = new RippleFilter();
        BufferedImage effectImage = rippleFilter.filter(baseImage, null);
        graph.drawImage(effectImage, 0, 0, null, null);
        graph.dispose();
        return distortedImage;
    }
}
