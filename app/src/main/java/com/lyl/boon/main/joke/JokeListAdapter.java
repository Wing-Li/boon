package com.lyl.boon.main.joke;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lyl.boon.R;
import com.lyl.boon.api.img.ImgUtils;
import com.lyl.boon.api.net.Network;
import com.lyl.boon.app.MyApp;
import com.lyl.boon.entity.ZhuangbiEntiry;
import com.lyl.boon.framework.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class JokeListAdapter extends MyBaseAdapter<ZhuangbiEntiry> {
    private Context context;

    public JokeListAdapter(Context context, List<ZhuangbiEntiry> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, ZhuangbiEntiry item) {
        super.onBind(holder, viewType, position, item);

        holder.setText(R.id.item_grid_title, item.getDescription());

        final String url = item.getImage_url();

        boolean gif = url.endsWith("gif");
        if (gif) {
            ImgUtils.loadGif(context, url, (ImageView) holder.getView(R.id.item_grid_img));
        } else {
            ImgUtils.loadC(context, url, (ImageView) holder.getView(R.id.item_grid_img));
        }
        String[] split = url.split("/");
        final String imgName = split[split.length - 1];

        holder.getView(R.id.item_grid_img).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext).setTitle(context.getString(R.string.image_save))//
                        .setMessage(context.getString(R.string.image_save_msg))//
                        .setNegativeButton(context.getString(R.string.save), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadImage(url, imgName);
                            }
                        })//
                        .setPositiveButton(context.getString(R.string.cancel), null).create().show();
                return false;
            }
        });
    }

    private void downloadImage(String url, final String imgName) {
        Call<ResponseBody> responseBodyCall = Network.getZhuangbi().downloadFileWithDynamicUrlSync(url);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), imgName);
                    if (writtenToDisk) {
                        Toast.makeText(mContext, R.string.save_success, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, R.string.down_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, R.string.down_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String imgName) {
        try {
            File futureStudioIconFile = new File(MyApp.getAppPath() + File.separator + imgName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[1024];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
