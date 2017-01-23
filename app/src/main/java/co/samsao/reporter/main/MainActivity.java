package co.samsao.reporter.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.samsao.reporter.R;
import co.samsao.reporter.adapter.RepositoryListAdapter;
import co.samsao.reporter.adapter.SimpleDividerItemDecoration;
import co.samsao.reporter.event.RepositoryClickEvent;
import co.samsao.reporter.model.Repository;

/**
 * The screen showing all the repositories.
 */
public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private IMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // setting the recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mRecyclerView.setAdapter(new RepositoryListAdapter(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.load();
            }
        });

        // setting the presenter
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        mPresenter.load();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRepositoryClickEvent(RepositoryClickEvent event) {
        showDetailsDialog(event.getRepository());
    }

    /**
     * Creates an AlertDialog to show repository details.
     *
     * @param repository the repository
     */
    private void showDetailsDialog(Repository repository) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(repository.getFullName());

        // getting the custom layout for the dialog and filling in information
        View dialogLayout = getLayoutInflater().inflate(R.layout.repository_info, null);
        TextView languageTextView = (TextView) dialogLayout.findViewById(R.id.language_text_view);
        languageTextView.setText(repository.getLanguage());
        TextView defaultBranchTextView = (TextView) dialogLayout.findViewById(R.id.default_branch_text_view);
        defaultBranchTextView.setText(repository.getDefaultBranch());
        TextView forksCountTextView = (TextView) dialogLayout.findViewById(R.id.forks_count_text_view);
        forksCountTextView.setText("" + repository.getForksCount());

        // showing the dialog
        builder.setView(dialogLayout);
        builder.setPositiveButton(getString(android.R.string.ok), null);
        builder.show();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
