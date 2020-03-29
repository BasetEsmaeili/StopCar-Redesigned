package ir.esmaeili.stopcar.utils;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ir.esmaeili.stopcar.adapter.ManageCarsAdapter;

public class RecyclerViewSwipeHelper extends ItemTouchHelper.SimpleCallback {
    private ManageCarsAdapter adapter;

    public RecyclerViewSwipeHelper(ManageCarsAdapter adapter) {
        super(0, ItemTouchHelper.END);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ManageCarsAdapter.ManageCarsToolbarViewHolder) return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.END) {
            adapter.deleteCar(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
        itemView.setAlpha(alpha);

    }
}
