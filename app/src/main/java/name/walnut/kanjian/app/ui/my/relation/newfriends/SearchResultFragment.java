package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.QueryUserAction;

/**
 * 搜索结果 fragment
 */
public class SearchResultFragment extends ActionBarFragment{

    public static final String QUERY = "query";

    @ResourceWeave(actionClass = QueryUserAction.class)
    public Resource queryUserResource;

    public static SearchResultFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(QUERY, query);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_fragment_search_result);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query = getArguments().getString(QUERY);
        queryUserResource.addParam("mobilephone", query)
                .send();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
