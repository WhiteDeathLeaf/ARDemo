//================================================================================================================================
//
//  Copyright (c) 2015-2017 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
//  EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
//  and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.galaxylight.ardemo.ar;

import com.galaxylight.ardemo.custom.ARVideo;
import com.galaxylight.ardemo.renderer.VideoRenderer;

import java.util.ArrayList;

import cn.easyar.Frame;
import cn.easyar.ImageTarget;
import cn.easyar.Target;
import cn.easyar.TargetInstance;
import cn.easyar.TargetStatus;

/**
 * Created by gzh on 2017-10-18.
 */
public class VideoAR extends BaseAR {
    private ArrayList<VideoRenderer> video_renderers;
    private VideoRenderer current_video_renderer;
    private int tracked_target = 0;
    private int active_target = 0;
    private ARVideo video = null;

    @Override
    public void dispose() {
        super.dispose();
        if (video != null) {
            video.dispose();
            video = null;
        }
        tracked_target = 0;
        active_target = 0;
        video_renderers.clear();
        current_video_renderer = null;
    }

    @Override
    public void initGL() {
        super.initGL();
        if (active_target != 0) {
            video.onLost();
            video.dispose();
            video = null;
            tracked_target = 0;
            active_target = 0;
        }
        video_renderers = new ArrayList<>();
        for (int k = 0; k < 3; k += 1) {
            VideoRenderer video_renderer = new VideoRenderer();
            video_renderer.init();
            video_renderers.add(video_renderer);
        }
        current_video_renderer = null;
    }

    @Override
    public void doRender(Frame frame) {
        ArrayList<TargetInstance> targetInstances = frame.targetInstances();
        if (targetInstances.size() > 0) {
            TargetInstance targetInstance = targetInstances.get(0);
            Target target = targetInstance.target();
            int status = targetInstance.status();
            if (status == TargetStatus.Tracked) {
                int id = target.runtimeID();
                if (active_target != 0 && active_target != id) {
                    video.onLost();
                    video.dispose();
                    video = null;
                    tracked_target = 0;
                    active_target = 0;
                }
                if (tracked_target == 0) {
                    if (video == null && video_renderers.size() > 0) {
                        String target_name = target.name();
                        if (target_name.equals("argame") && video_renderers.get(0).texId() != 0) {
                            video = new ARVideo();
                            video.openVideoFile("video.mp4", video_renderers.get(0).texId());
                            current_video_renderer = video_renderers.get(0);
                        } else if (target_name.equals("namecard") && video_renderers.get(1).texId() != 0) {
                            video = new ARVideo();
                            video.openTransparentVideoFile("transparentvideo.mp4", video_renderers.get(1).texId());
                            current_video_renderer = video_renderers.get(1);
                        } else if (target_name.equals("idback") && video_renderers.get(2).texId() != 0) {
                            video = new ARVideo();
                            video.openStreamingVideo("https://sightpvideo-cdn.sightp.com/sdkvideo/EasyARSDKShow201520.mp4", video_renderers.get(2).texId());
                            current_video_renderer = video_renderers.get(2);
                        }
                    }
                    if (video != null) {
                        video.onFound();
                        tracked_target = id;
                        active_target = id;
                    }
                }
                ImageTarget imagetarget = target instanceof ImageTarget ? (ImageTarget) (target) : null;
                if (imagetarget != null) {
                    if (current_video_renderer != null) {
                        video.update();
                        if (video.isRenderTextureAvailable()) {
                            current_video_renderer.render(camera.projectionGL(0.2f, 500.f), targetInstance.poseGL(), imagetarget.size());
                        }
                    }
                }
            }
        } else {
            if (tracked_target != 0) {
                video.onLost();
                tracked_target = 0;
            }
        }
    }
}
