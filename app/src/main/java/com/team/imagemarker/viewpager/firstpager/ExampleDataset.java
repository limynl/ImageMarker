package com.team.imagemarker.viewpager.firstpager;

import com.team.imagemarker.R;
import com.wangjie.rapidfloatingactionbutton.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleDataset {

    private List<ECCardData> dataset;
//    private List<Comment> comments;

    public ExampleDataset() {
        dataset = new ArrayList<>(5);
//        comments = Arrays.asList(
//                new Comment(R.drawable.aaron_bradley, "Aaron Bradley", "When the sensor experiments for deep space, all mermaids accelerate mysterious, vital moons.", "jan 12, 2014"),
//                new Comment(R.drawable.barry_allen, "Barry Allen", "It is a cold powerdrain, sir.", "jun 1, 2015"),
//                new Comment(R.drawable.bella_holmes, "Bella Holmes", "Particle of a calm shield, control the alignment!", "sep 21, 1937"),
//                new Comment(R.drawable.caroline_shaw, "Caroline Shaw", "The human kahless quickly promises the phenomenan.", "may 23, 1967"),
//                new Comment(R.drawable.connor_graham, "Connor Graham", "Ionic cannon at the infinity room was the sensor of voyage, imitated to a dead pathway.", "sep 1, 1972"),
//                new Comment(R.drawable.deann_hunt, "Deann Hunt", "Vital particles, to the port.", "aug 13, 1995"),
//                new Comment(R.drawable.ella_cole, "Ella Cole", "Stars fly with hypnosis at the boldly infinity room!", "nov 18, 1952"),
//                new Comment(R.drawable.jayden_shaw, "Jayden Shaw", "Hypnosis, definition, and powerdrain.", "apr 1, 2013"),
//                new Comment(R.drawable.jerry_carrol, "Jerry Carrol", "When the queen experiments for nowhere, all particles control reliable, cold captains.", "nov 14, 1964"),
//                new Comment(R.drawable.lena_lucas, "Lena Lukas", "When the c-beam experiments for astral city, all cosmonauts acquire remarkable, virtual lieutenant commanders.", "may 4, 1965"),
//                new Comment(R.drawable.leonrd_kim, "Leonard Kim", "Starships walk with love at the cold parallel universe!", "jul 3, 1974"),
//                new Comment(R.drawable.marc_baker, "Mark Baker", "Friendship at the bridge that is when quirky green people yell.", "dec 24, 1989"));

        CardData item5 = new CardData();
        item5.setMainBackgroundResource(R.mipmap.system_push1);
        item5.setHeadBackgroundResource(R.mipmap.system_push1);
        item5.setHeadTitle("Attractions");
        item5.setPersonMessage("这是简要信息");
        item5.setPersonName("这是标题");
        item5.setPersonPictureResource(R.mipmap.default_avatar);
//        item5.setListItems(prepareCommentsArray());
        dataset.add(item5);

        CardData item4 = new CardData();
        item4.setMainBackgroundResource(R.mipmap.system_push2);
        item4.setHeadBackgroundResource(R.mipmap.system_push2);
        item4.setHeadTitle("City Scape");
        item4.setPersonMessage("这是简要信息");
        item4.setPersonName("这是标题");
        item4.setPersonPictureResource(R.mipmap.default_avatar);
//        item4.setListItems(prepareCommentsArray());
        dataset.add(item4);

        CardData item3 = new CardData();
        item3.setMainBackgroundResource(R.mipmap.system_push3);
        item3.setHeadBackgroundResource(R.mipmap.system_push3);
        item3.setHeadTitle("Cuisine");
        item3.setPersonMessage("这是简要信息");
        item3.setPersonName("这是标题");
        item3.setPersonPictureResource(R.mipmap.default_avatar);
//        item3.setListItems(prepareCommentsArray());
        dataset.add(item3);

        CardData item2 = new CardData();
        item2.setMainBackgroundResource(R.mipmap.system_push4);
        item2.setHeadBackgroundResource(R.mipmap.system_push4);
        item2.setHeadTitle("Nature");
        item2.setPersonName("这是标题");
        item2.setPersonMessage("这是简要信息");
//        item2.setListItems(prepareCommentsArray());
        item2.setPersonPictureResource(R.mipmap.default_avatar);
        dataset.add(item2);

        CardData item1 = new CardData();
        item1.setMainBackgroundResource(R.mipmap.system_push5);
        item1.setHeadBackgroundResource(R.mipmap.system_push5);
        item1.setHeadTitle("Night Life");
        item1.setPersonMessage("这是简要信息");
        item1.setPersonName("这是标题");
        item1.setPersonPictureResource(R.mipmap.default_avatar);
//        item1.setListItems(prepareCommentsArray());
        dataset.add(item1);

    }

    public List<ECCardData> getDataset() {
        Collections.shuffle(dataset);
        return dataset;
    }

//    private List<Comment> prepareCommentsArray() {
//        Random random = new Random();
//        Collections.shuffle(comments);
//        return comments.subList(0, 6 + random.nextInt(5));
//    }
}
