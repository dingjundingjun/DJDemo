package com.example.Activity;

import java.util.Arrays;
import java.util.List;

import com.bbk.example.demo.R;
import com.twotoasters.jazzylistview.ActivityInfo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListDemoActivity extends ListActivity
{
	public static final String EXTRA_TITLE_RES = "title_res";
    public static final String EXTRA_ITEM_LAYOUT_RES = "item_layout_res";
    public static final String EXTRA_IS_STAGGERED = "is_staggered";

    private final List<ActivityInfo> activityInfos = Arrays.asList(
            new ActivityInfo(ListViewActivity.class, R.string.listview_example, R.layout.item, false),
            new ActivityInfo(GridViewActivity.class, R.string.gridview_example, R.layout.grid_item, false)
           
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] titles = getActivityTitles();
        setListAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1, titles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ActivityInfo activityInfo = activityInfos.get(position);
        Class<? extends Activity> clazz = activityInfo.activityClass;
        startActivity(new Intent(this, clazz)
                .putExtra(EXTRA_TITLE_RES, activityInfo.titleRes)
                .putExtra(EXTRA_ITEM_LAYOUT_RES, activityInfo.itemLayoutRes)
                .putExtra(EXTRA_IS_STAGGERED, activityInfo.isStaggered));
    }

    private String[] getActivityTitles() {
        String[] result = new String[activityInfos.size()];
        int i = 0;
        for (ActivityInfo info : activityInfos) {
            result[i++] = getString(info.titleRes);
        }
        return result;
    }
}
