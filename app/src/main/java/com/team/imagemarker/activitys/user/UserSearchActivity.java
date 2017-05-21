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
        tagList.add("清新花园派对");
        tagList.add("防晒衣外套");
        tagList.add("情路装短袖");
        tagList.add("小白鞋");
        tagList.add("吊带背心");
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
