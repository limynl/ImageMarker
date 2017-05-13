package com.team.imagemarker.activitys.user;

import android.app.Activity;
import android.os.Bundle;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/5/4.
 * email 1434117404@qq.com
 */

public class UserSearchActivity extends Activity {
    private TagGroup tagGroup;
    private List<String> tagList = new ArrayList<String>();
    private int hotIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);

        tagList.add("三国之超级召唤系统");
        tagList.add("鬼医嫡妃");
        tagList.add("文艺大明星");
        tagList.add("鬼村扎纸人");
        tagList.add("私密宝贝，帝少kissme");
        tagList.add("执掌龙宫");
        tagList.add("大唐太子爷");
        tagList.add("重生之改天换地");

        showHotWord();
    }

    private void showHotWord() {
        int tagSize = 8;//每次显示8个
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < tagList.size(); hotIndex++, j++) {
            tags[j] = tagList.get(hotIndex % tagList.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        tagGroup.setTags(colors, tags);
    }



}
