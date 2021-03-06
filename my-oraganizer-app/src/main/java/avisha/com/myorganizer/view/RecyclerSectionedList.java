package avisha.com.myorganizer.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;

import avisha.com.myorganizer.R;

/**
 * Created by swaroop.kulkarni on 3/15/2016.
 */
public class RecyclerSectionedList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final int mSectionResourceId;
    private final int mTextResourceId;
    private final RecyclerAdapter mBaseAdapter;
    private final Context mContext;
    private boolean mValid = true;
    private SparseArray<Section> mSections = new SparseArray<Section>();
    private final int SECTION_TYPE1 = 0;

    public RecyclerSectionedList(Context context, int sectionResourceId, int textResourceId,
                                 RecyclerAdapter baseAdapter) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSectionResourceId = sectionResourceId;
        mTextResourceId = textResourceId;
        mBaseAdapter = baseAdapter;
        mContext = context;

        mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_TYPE1) {
            final View view = LayoutInflater.from(mContext).inflate(mSectionResourceId, parent, false);
            return new SectionViewHolder(view, mTextResourceId);
        } else {
            return mBaseAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
        if (getSectionHeaderPosition(position)) {
            Section section = mSections.get(position);
            SectionViewHolder sectionViewHolder1 = ((SectionViewHolder) sectionViewHolder);
            sectionViewHolder1.title.setText(section.title);
        } else {
            mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        return (mValid ? mBaseAdapter.getItemCount() + mSections.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;
        if (getSectionHeaderPosition(position)) {
            viewType = SECTION_TYPE1;
        } else {
            viewType = mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
        }
        return viewType;
    }

    public boolean getSectionHeaderPosition(int position) {
        return mSections.get(position) != null;
    }

    public void setSections(Section[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Section>() {
            @Override
            public int compare(Section o, Section o1) {
                return (o.firstPosition == o1.firstPosition)
                        ? 0
                        : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
            }
        });

        int offset = 0; // offset positions for the headers we're adding
        for (Section section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            mSections.append(section.sectionedPosition, section);
            ++offset;
        }

        notifyDataSetChanged();
    }

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).firstPosition > position) {
                break;
            }
            ++offset;
        }
        return position + offset;
    }

    public int sectionedPositionToPosition(int sectionedPosition) {
        if (getSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break;
            }
            --offset;
        }
        return sectionedPosition + offset;
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        View view;

        public SectionViewHolder(View v, int mTextResourceid) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(mTextResourceid);
        }
    }

    public static class Section {
        int firstPosition;
        int sectionedPosition;
        CharSequence title;

        public Section(int firstPosition, CharSequence title) {
            this.firstPosition = firstPosition;
            this.title = title;
        }

        public CharSequence getTitle() {
            return title;
        }
    }
}