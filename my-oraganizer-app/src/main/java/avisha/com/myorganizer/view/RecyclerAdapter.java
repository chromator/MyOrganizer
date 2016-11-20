package avisha.com.myorganizer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import avisha.com.myorganizer.R;
import avisha.com.myorganizer.model.DataModal;
import avisha.com.myorganizer.model.MOTask;

/**
 * Created by swaroop.kulkarni on 3/8/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MOTask> mDataModalList;
    private OnVersionNameSelectionChangeListener mListener;

    public RecyclerAdapter(Context context, List<MOTask> dataModals) {
        mDataModalList = dataModals;
        mContext = context;
    }

    public OnVersionNameSelectionChangeListener getListener() {
        return mListener;
    }

    public void setListener(OnVersionNameSelectionChangeListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MOTask dataModal = mDataModalList.get(position);
        RecycleViewHolder holder = (RecycleViewHolder)viewHolder;
        holder.mDataModal = dataModal;
        holder.mListTitle.setText(dataModal.getName());
        holder.mListDescrion.setText(dataModal.getEmail().toString());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) mListener.OnSelectionChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataModalList.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        View mView;
        MOTask mDataModal;
        ImageView mListIcon;
        TextView mListTitle;
        TextView mListDescrion;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mListIcon = (ImageView) itemView.findViewById(R.id.list_icon);
            mListTitle = (TextView) itemView.findViewById(R.id.list_title);
            mListDescrion = (TextView) itemView.findViewById(R.id.list_desc);
        }
    }
}