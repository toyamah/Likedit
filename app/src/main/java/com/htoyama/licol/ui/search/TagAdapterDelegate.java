package com.htoyama.licol.ui.search;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegate;
import com.htoyama.licol.R;
import com.htoyama.licol.databinding.ViewSearchAssistTagBinding;
import com.htoyama.licol.domain.tag.Tag;
import com.htoyama.licol.ui.common.BindingHolder;

import static com.htoyama.licol.common.Contract.requireNotNull;

public class TagAdapterDelegate implements AdapterDelegate<Assist> {

  interface OnTagClickListener {
    void onTagClick(Tag tag);
  }

  private final OnTagClickListener listener;

  public TagAdapterDelegate(OnTagClickListener listener) {
    this.listener = requireNotNull(listener, "listener");
  }

  @Override public boolean isForViewType(@NonNull Assist items, int position) {
    return items.get(position) instanceof Tag;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
    return new ViewHolder(parent, R.layout.view_search_assist_tag);
  }

  @Override
  public void onBindViewHolder(@NonNull Assist items, int position, @NonNull RecyclerView.ViewHolder holder) {
    ViewSearchAssistTagBinding binding = ((ViewHolder) holder).binding;
    Tag tag = (Tag) items.get(position);
    binding.setTag(tag);
    binding.getRoot().setOnClickListener(v -> listener.onTagClick(tag));
  }

  private static class ViewHolder extends BindingHolder<ViewSearchAssistTagBinding> {

    public ViewHolder(ViewGroup parent, @LayoutRes int layoutRes) {
      super(parent, layoutRes);
    }
  }
}