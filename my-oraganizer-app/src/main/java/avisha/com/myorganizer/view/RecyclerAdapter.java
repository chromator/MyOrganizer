package avisha.com.myorganizer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
        String name = dataModal.getName();
        RecycleViewHolder holder = (RecycleViewHolder)viewHolder;
        holder.mDataModal = dataModal;

        if(!TextUtils.isEmpty(name)) {
            holder.mListIcon.setText(""+name.toUpperCase().charAt(0));
            holder.mListTitle.setText(name);
        }

        holder.mListDescrion.setText(dataModal.getEmail().toString());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) mListener.OnSelectionChanged(position);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(null != mListener) {
                    mListener.onLongClick(position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataModalList.size();
    }

    public int getItemIndex(MOTask moTask) {
        return mDataModalList.indexOf(moTask);
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        View mView;
        MOTask mDataModal;
        TextView mListIcon;
        TextView mListTitle;
        TextView mListDescrion;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mListIcon = (TextView) itemView.findViewById(R.id.list_icon);
            mListTitle = (TextView) itemView.findViewById(R.id.list_title);
            mListDescrion = (TextView) itemView.findViewById(R.id.list_desc);
        }
    }
}