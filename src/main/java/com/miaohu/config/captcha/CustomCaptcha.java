package com.miaohu.config.captcha;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by fz on 17-4-5.
 * 自定义验证码图形
 */
public class CustomCaptcha extends Configurable implements GimpyEngine
{
    public BufferedImage getDistortedImage(BufferedImage baseImage)
    {
        // 干扰线
//        NoiseProducer noiseProducer = getConfig().getNoiseImpl();
//        rippleFilter.setWaveType(RippleFilter.SINE);
//        rippleFilter.setEdgeAction(TransformFilter.BILINEAR);
//        noiseProducer.makeNoise(distortedImage, .1f, .07f, .3f, .9f);

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
