package ir.esmaeili.stopcar.utils;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.esmaeili.stopcar.R;
import ir.esmaeili.stopcar.adapter.IntroPagerAdapter;
import ir.esmaeili.stopcar.models.AlertView;
import ir.esmaeili.stopcar.models.EventType;
import ir.esmaeili.stopcar.models.Slide;
import ir.esmaeili.stopcar.ui.base.BaseViewModel;
import ir.esmaeili.stopcar.ui.fragments.cars.NewCarDialogViewModel;
import ir.esmaeili.stopcar.ui.fragments.newcar.CarColorPickerViewModel;
import ir.esmaeili.stopcar.ui.fragments.newcar.NewCarViewModel;

public class DataBindingTools {
    @BindingAdapter(value = {"items"}, requireAll = false)
    public static void setIntroAdapter(ViewPager2 pager, List<Slide> items) {
        IntroPagerAdapter pagerAdapter = new IntroPagerAdapter(items);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(pagerAdapter);
    }

    @BindingAdapter(value = {"glideResource"}, requireAll = false)
    public static void glideBindLocal(ImageView img, int resourceID) {
        Glide.with(img).load(resourceID).into(img);
    }

    @BindingAdapter(value = {"changeListener"}, requireAll = false)
    public static <V extends BaseViewModel> void EditTextChangeListener(final TextInputLayout editText, final V viewModel) {
        if (viewModel instanceof NewCarDialogViewModel) {
            NewCarDialogViewModel newCarDialogViewModel = ((NewCarDialogViewModel) viewModel);
            editText.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    switch (editText.getId()) {
                        case R.id.input_new_car_name:
                            newCarDialogViewModel.setCarName(editable.toString());
                            break;
                        case R.id.input_new_car_model:
                            newCarDialogViewModel.setCarModel(editable.toString());
                            break;
                        case R.id.input_new_car_ir_cde:
                            newCarDialogViewModel.setCarPlaqueIrCode(editable.toString());
                            break;
                        case R.id.input_new_car_three_part:
                            newCarDialogViewModel.setCarPlaquePartThreeCode(editable.toString());
                            break;
                        case R.id.input_new_car_keyword:
                            newCarDialogViewModel.setCarPlaqueKeyWord(editable.toString());
                            break;
                        case R.id.input_new_car_two_part:
                            newCarDialogViewModel.setCarPlaquePartTwo(editable.toString());
                            break;
                    }
                }
            });
        } else if (viewModel instanceof NewCarViewModel) {
            NewCarViewModel newCarViewModel = ((NewCarViewModel) viewModel);
            editText.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    switch (editText.getId()) {
                        case R.id.input_new_car_name:
                            newCarViewModel.setCarName(editable.toString());
                            break;
                        case R.id.input_new_car_model:
                            newCarViewModel.setCarModel(editable.toString());
                            break;
                        case R.id.input_new_car_ir_cde:
                            newCarViewModel.setCarPlaqueIrCode(editable.toString());
                            break;
                        case R.id.input_new_car_three_part:
                            newCarViewModel.setCarPlaquePartThreeCode(editable.toString());
                            break;
                        case R.id.input_new_car_keyword:
                            newCarViewModel.setCarPlaqueKeyWord(editable.toString());
                            break;
                        case R.id.input_new_car_two_part:
                            newCarViewModel.setCarPlaquePartTwo(editable.toString());
                            break;
                    }
                }
            });
        }

    }

    @BindingAdapter(value = {"onToolbarIconClick", "sharedViewModel"}, requireAll = false)
    public static <V extends BaseViewModel> void bindToolbarClickListener(Toolbar toolbar, V model, SharedViewModel sharedViewModel) {
        if (model instanceof NewCarViewModel) {
            NewCarViewModel viewModel = ((NewCarViewModel) model);
            toolbar.setNavigationOnClickListener(p0 -> {
                if (viewModel.isNewCarInputsNotEmpty()) {
                    viewModel.insertCar();
                } else {
                    sharedViewModel.setAlertEvent(new AlertView.CustomToast(EventType.ALERT,
                            toolbar.getContext().getResources().getString(R.string.label_error_input),
                            toolbar.getContext().getResources().getString(R.string.label_error_input_description),
                            R.drawable.ic_report_problem_24dp));
                }
            });
        } else if (model instanceof NewCarDialogViewModel) {
            NewCarDialogViewModel viewModel = ((NewCarDialogViewModel) model);
            toolbar.setNavigationOnClickListener(view -> {
                if (viewModel.isNewCarInputsNotEmpty()) {
                    viewModel.insertCar();
                } else {
                    sharedViewModel.setAlertEvent(new AlertView.CustomToast(EventType.ALERT,
                            toolbar.getContext().getResources().getString(R.string.label_error_input),
                            toolbar.getContext().getResources().getString(R.string.label_error_input_description),
                            R.drawable.ic_report_problem_24dp));
                }
            });
        }
    }

    @BindingAdapter(value = {"setRecyclerViewScrollListener"})
    public static void setRecyclerViewScrollListener(RecyclerView recyclerView, ExtendedFloatingActionButton button) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && button.isShown()) {
                    button.hide();
                } else if (dy <= 0) {
                    button.show();
                }
            }
        });
    }

    @BindingAdapter(value = {"itemsDecoration"})
    public static void setItemDecoration(RecyclerView recyclerView, int decoration) {
        recyclerView.addItemDecoration(new MarginItemDecoration(decoration));
    }

    @BindingAdapter(value = {"colorFilter"}, requireAll = false)
    public static void setImageViewDrawableColor(ImageView imageView, int color) {
        imageView.setColorFilter(color);
    }
}
