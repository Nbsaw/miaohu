package com.zhihu.config.captcha;

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
        NoiseProducer noiseProducer = getConfig().getNoiseImpl();
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(),
                baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = (Graphics2D) distortedImage.getGraphics();
        RippleFilter rippleFilter = new RippleFilter();
        rippleFilter.setWaveType(RippleFilter.SINE);
        rippleFilter.setEdgeAction(TransformFilter.BILINEAR);
        BufferedImage effectImage = rippleFilter.filter(baseImage, null);
        graph.drawImage(effectImage, 0, 0, null, null);
        graph.dispose();
        noiseProducer.makeNoise(distortedImage, .1f, .07f, .3f, .9f);
        return distortedImage;
    }
}
