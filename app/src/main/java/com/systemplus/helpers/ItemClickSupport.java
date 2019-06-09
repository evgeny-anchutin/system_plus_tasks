package com.systemplus.helpers;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.lang.ref.WeakReference;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ItemClickSupport
 */
public class ItemClickSupport { // todo[do]: convert to kotlin

    private static final String TAG = "debug";

    private final WeakReference<RecyclerView> mRecyclerViewLink;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private SparseArray<OnItemViewClickListener> mOnItemViewsClickListeners = new SparseArray<>();

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener == null) {
                Log.w(TAG, "OnItemClickListener is null");
                return;
            }
            int adapterPosition = getAdapterPositionByView(v);
            mOnItemClickListener.onItemClicked(getRecyclerView(), adapterPosition, v);
        }
    };
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener == null) {
                Log.w(TAG, "OnItemLongClickListener is null");
                return false;
            }

            int adapterPosition = getAdapterPositionByView(v);
            return mOnItemLongClickListener.onItemLongClicked(getRecyclerView(), adapterPosition, v);
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(onLongClickListener);
            }

            for (int i = 0; i < mOnItemViewsClickListeners.size(); i++) {
                int viewId = mOnItemViewsClickListeners.keyAt(i);
                OnItemViewClickListener listener = mOnItemViewsClickListeners.get(viewId);
                view.findViewById(viewId).setOnClickListener(v -> {
                    int adapterPosition = getAdapterPositionByView(view);
                    listener.onItemViewClicked(getRecyclerView(), adapterPosition, v);
                });
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            // do nothing
        }
    };

    private int getAdapterPositionByView(View view) {
        final RecyclerView vRecycler = getRecyclerView();
        RecyclerView.ViewHolder holder = vRecycler.getChildViewHolder(view);
        return holder.getAdapterPosition();
    }

    private ItemClickSupport(RecyclerView vRecycler) {
        mRecyclerViewLink = new WeakReference<>(vRecycler);
        vRecycler.setTag(this);
        vRecycler.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag();
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag();
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public ItemClickSupport addOnItemViewClickListener(@IdRes int viewId, OnItemViewClickListener listener) {
        mOnItemViewsClickListeners.put(viewId, listener);
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(new Object());
    }

    /**
     * OnItemViewClickListener
     */
    public interface OnItemViewClickListener {
        void onItemViewClicked(RecyclerView vList, int position, View v);
    }

    /**
     * OnItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClicked(RecyclerView vList, int position, View v);
    }

    /**
     * OnItemLongClickListener
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView vList, int position, View v);
    }

    @Nullable
    private RecyclerView getRecyclerView() {
        return mRecyclerViewLink.get();
    }
}