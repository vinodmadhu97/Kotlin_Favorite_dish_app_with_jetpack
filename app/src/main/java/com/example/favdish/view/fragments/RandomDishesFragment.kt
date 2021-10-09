package com.example.favdish.view.fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.favdish.R
import com.example.favdish.application.FavDishApplication
import com.example.favdish.databinding.FragmentRandomDishesBinding
import com.example.favdish.model.databse.FavDishRepository
import com.example.favdish.model.entities.FavDish
import com.example.favdish.model.entities.RandomDish
import com.example.favdish.utils.Constants
import com.example.favdish.viewModel.FavDishViewModel
import com.example.favdish.viewModel.FavDishViewModelFactory
import com.example.favdish.viewModel.NotificationsViewModel
import com.example.favdish.viewModel.RandomDishViewModel

class RandomDishesFragment : Fragment() {

    private var mBinding : FragmentRandomDishesBinding? = null
    private lateinit var mRandomDishModel: RandomDishViewModel
    private var mProgressDialog : Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentRandomDishesBinding.inflate(inflater,container,false)


        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)
        mRandomDishModel.getRandomDishFromApi()
        randomDishViewModelObserver()

        mBinding!!.srlRandomDish.setOnRefreshListener {
            mRandomDishModel.getRandomDishFromApi()
        }

    }


    private fun randomDishViewModelObserver(){
        mRandomDishModel.randomDishResponse.observe(viewLifecycleOwner,
            {
                randomDishResponse -> randomDishResponse?.let {
                   //Log.i("random_dish","${randomDishResponse.recipes[0]}")
                setRandomDishResponseInUI(randomDishResponse.recipes[0])

                    if (mBinding!!.srlRandomDish.isRefreshing){
                        mBinding!!.srlRandomDish.isRefreshing = false
                    }

                }
            }
            )

        mRandomDishModel.randomDishLoadingError.observe(viewLifecycleOwner,
            {
                dataError -> dataError?.let {
                   Log.e("random_dish_error","$dataError")
                if (mBinding!!.srlRandomDish.isRefreshing){
                    mBinding!!.srlRandomDish.isRefreshing = false
                }
                }
            }
            )

        mRandomDishModel.loadRandomDish.observe(viewLifecycleOwner,{
            loadRandomDish -> loadRandomDish?.let {
                //Log.e("random_dish_loading","$loadRandomDish")
                //IF DATA LOADING STARTED loadingRandomDish is TRUE AFTER THE LOADING THAT WILL BE FALSE
                // SCROLL DOWN REFRESHING HAS OUN PROGRESS DIALOG
                if (loadRandomDish && !mBinding!!.srlRandomDish.isRefreshing){
                    showProgressDialog()
                }else{
                    dismissDialog()
                }
            }
        })
    }

    private fun setRandomDishResponseInUI(recipe : RandomDish.Recipe){
        Glide.with(requireActivity())
            .load(recipe.image)
            .centerCrop()
            .into(mBinding!!.ivDishImage)


        var dishType = "other"

        if (recipe.dishTypes.isNotEmpty()){
            dishType = recipe.dishTypes[0]
            mBinding!!.tvType.text = dishType
        }
        mBinding!!.tvCategory.text = "Other"

        var ingredients = ""
        for (value in recipe.extendedIngredients){
            if (ingredients.isEmpty()){
                ingredients = value.original
            }else{
                ingredients = ingredients + ", \n" + value.original
            }
        }

        mBinding!!.tvIngredients.text = ingredients

        //THIS DATA COME AS HTML FORMAT FROM API
        //THAT DATA CONVERTING TO THE XML
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mBinding!!.tvCookingDirection.text = Html.fromHtml(
                //this data is html text
                recipe.instructions,
                Html.FROM_HTML_MODE_COMPACT
            )
        }else{
            @Suppress("DEPRECATION")
            mBinding!!.tvCookingDirection.text = Html.fromHtml(recipe.instructions,)
        }

        mBinding!!.tvCookingTime.text = resources.getString(
            R.string.lbl_estimate_cooking_time,
            recipe.readyInMinutes.toString())

        mBinding!!.ivFavoriteDish.setImageDrawable(
            ContextCompat.getDrawable(requireActivity(),
                R.drawable.ic_favorite_unselected))

        var addedToFavorite = false



            mBinding!!.ivFavoriteDish.setOnClickListener {

                if (addedToFavorite){
                    Toast.makeText(requireActivity(),resources.getString(R.string.msg_already_added_to_the_favorite),Toast.LENGTH_SHORT).show()
                }else{
                    val randomDishDetails = FavDish(
                        recipe.image,
                        Constants.DISH_IMAGE_SOURCE_ONLINE,
                        recipe.title,
                        dishType,
                        "Other",
                        ingredients,
                        recipe.readyInMinutes.toString(),
                        recipe.instructions,
                        true
                    )



                    val favDishViewModel : FavDishViewModel by viewModels {
                        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
                    }
                    favDishViewModel.insert(randomDishDetails)

                    mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_favorite_selected))
                    Toast.makeText(requireActivity(),"Dish added to the Favorite",Toast.LENGTH_SHORT).show()

                    addedToFavorite = true
                }

            }

    }

    private fun showProgressDialog(){
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog?.let {
            it.setContentView(R.layout.custom_progress_dialog)
            it.show()
        }
    }

    private fun dismissDialog(){
        mProgressDialog?.let {
            it.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}