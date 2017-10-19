//================================================================================================================================
//
//  Copyright (c) 2015-2017 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
//  EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
//  and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.galaxylight.ardemo.ar;

import com.galaxylight.ardemo.renderer.BoxRenderer;

import cn.easyar.Frame;
import cn.easyar.ImageTarget;
import cn.easyar.Target;
import cn.easyar.TargetInstance;
import cn.easyar.TargetStatus;

/**
 * Created by gzh on 2017-10-18.
 */
public class ImageAR extends BaseAR {
    private BoxRenderer box_renderer;

    @Override
    public void dispose() {
        super.dispose();
        box_renderer = null;
    }

    @Override
    public void initGL() {
        super.initGL();
        box_renderer = new BoxRenderer();
        box_renderer.init();
    }

    @Override
    public void doRender(Frame frame) {
        for (TargetInstance targetInstance : frame.targetInstances()) {
            int status = targetInstance.status();
            if (status == TargetStatus.Tracked) {
                Target target = targetInstance.target();
                ImageTarget imagetarget = target instanceof ImageTarget ? (ImageTarget) (target) : null;
                if (imagetarget == null) {
                    continue;
                }
                if (box_renderer != null) {
                    box_renderer.render(camera.projectionGL(0.2f, 500.f), targetInstance.poseGL(), imagetarget.size());
                }
            }
        }
    }
}
