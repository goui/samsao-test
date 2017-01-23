package co.samsao.reporter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.samsao.reporter.R;
import co.samsao.reporter.model.Repository;
import co.samsao.reporter.model.RepositoryModel;

/**
 * Adapter for a recycler view displaying all the repositories.
 */
public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder> implements Observer {

    private LayoutInflater mLayoutInflater;

    private List<Repository> mListOfRepositories;

    public RepositoryListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        RepositoryModel.getInstance().addObserver(this);
        mListOfRepositories = RepositoryModel.getInstance().getRepositories();
    }

    @Override
    public RepositoryListAdapter.RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(mLayoutInflater.inflate(R.layout.item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryListAdapter.RepositoryViewHolder holder, int position) {
        Repository repository = mListOfRepositories.get(position);
        if (repository != null) {
            holder.fullNameTextView.setText(repository.getFullName());
            holder.updatedTextView.setText(repository.getUpdatedTime().toString()); // TODO format date
            holder.descriptionTextView.setText(repository.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return mListOfRepositories.size();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof RepositoryModel) {
            mListOfRepositories = RepositoryModel.getInstance().getRepositories();
            notifyDataSetChanged();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        RepositoryModel.getInstance().deleteObserver(this);
    }

    /**
     * View holder representing one Repository item.
     */
    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.full_name_text_view)
        TextView fullNameTextView;

        @BindView(R.id.updated_text_view)
        TextView updatedTextView;

        @BindView(R.id.description_text_view)
        TextView descriptionTextView;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
