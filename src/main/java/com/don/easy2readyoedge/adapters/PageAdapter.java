package com.don.easy2readyoedge.adapters;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.beans.PageBean;
import com.don.easy2readyoedge.core.self.IFAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseViewHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DON on 16/08/29.
 */
public class PageAdapter extends SelfBaseAdapter {
  public interface PageAdapterListener{
//    void
  }
  public PageAdapter(final Activity activity, List data, IFAdapter ifAdapter) {
    super(R.layout.item_page, data, new IFAdapter<PageBean>() {
      @Override
      public void onSelfBindViewHolder(final SelfBaseViewHolder holder, List<PageBean> data, int position) {
        //应用区域
        final Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);

        Uri uri = Uri.parse(data.get(position).getPageUrl());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//          .setResizeOptions(new ResizeOptions(outRect1.width(), outRect1.height()))
          .build();
        DraweeController draweeController = Fresco.getDraweeControllerBuilderSupplier().get()
//          .setUri(uri)
          .setOldController(holder.getSimpleDraweeView(R.id.simple_drawee_view).getController())
          .setImageRequest(request)
          .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
          .setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
              String id,
              @Nullable final ImageInfo imageInfo,
              @Nullable Animatable anim) {
              if (imageInfo == null) {
                return;
              }
              QualityInfo qualityInfo = imageInfo.getQualityInfo();

              activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  ViewGroup.LayoutParams lp = holder.getSimpleDraweeView(R.id.simple_drawee_view).getLayoutParams();

                  float hnw = (float) (imageInfo.getHeight() * 1.0 / imageInfo.getWidth());

                  lp.width = outRect1.width();
                  lp.height = (int) (hnw * outRect1.width());
                  holder.getSimpleDraweeView(R.id.simple_drawee_view).setLayoutParams(lp);
                }
              });
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
              throwable.printStackTrace();
            }
          })
          .build();
        holder.getSimpleDraweeView(R.id.simple_drawee_view).setController(draweeController);
//        holder.getSimpleDraweeView(R.id.simple_drawee_view).setAspectRatio(0.70f);
      }
    });
  }
}
