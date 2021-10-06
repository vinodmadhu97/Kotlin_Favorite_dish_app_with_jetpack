package com.example.favdish.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.favdish.R
import com.example.favdish.application.FavDishApplication
import com.example.favdish.databinding.FragmentDishDetailsBinding
import com.example.favdish.model.databse.FavDishRepository
import com.example.favdish.viewModel.FavDishViewModel
import com.example.favdish.viewModel.FavDishViewModelFactory
import java.io.IOException


class DishDetailsFragment : Fragment() {

    private var mBinding:FragmentDishDetailsBinding? = null

    private val mFavDishViewModel : FavDishViewModel by viewModels {
        FavDishViewModelFactory(((requireActivity().application) as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentDishDetailsBinding.inflate(inflater,container,false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //after the creating the view then set the data come from navigator
        val args : DishDetailsFragmentArgs by navArgs()

        //Log.i("title",args.dishDetails.title)
        //Log.i("title",args.dishDetails.type)
        args.let {

            try{
                Glide.with(requireActivity())
                    .load(it.dishDetails.image)
                    .centerCrop()
                    .listener(object:RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("error","image loading error",e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.let {
                                Palette.from(resource.toBitmap()).generate(){
                                    palette ->
                                        val intColor = palette?.vibrantSwatch?.rgb ?: 0
                                        mBinding!!.rlDishDetailMain.setBackgroundColor(intColor)

                                }
                            }
                            return false
                        }

                    })
                    .into(mBinding!!.ivDishImage)
            }catch (e:IOException){
                e.printStackTrace()
            }

            mBinding!!.tvTitle.text = it.dishDetails.title
            mBinding!!.tvCategory.text = it.dishDetails.category
            mBinding!!.tvIngredients.text = it.dishDetails.ingredients
            mBinding!!.tvCookingDirection.text = it.dishDetails.directionToCook
            mBinding!!.tvCookingTime.text = resources.getString(R.string.lbl_estimate_cooking_time,it.dishDetails.cookingTime)

            if (args.dishDetails.favoriteDish){

                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_selected))
                Toast.makeText(requireContext(),"Added to the favorite",Toast.LENGTH_LONG).show()

            }else{

                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_unselected))
                Toast.makeText(requireContext(),"Remove from  the favorite",Toast.LENGTH_LONG).show()

            }

        }

        mBinding!!.ivFavoriteDish.setOnClickListener {
            args.dishDetails.favoriteDish = !args.dishDetails.favoriteDish
            mFavDishViewModel.update(args.dishDetails)

            if (args.dishDetails.favoriteDish){

                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_selected))
                Toast.makeText(requireContext(),"Added to the favorite",Toast.LENGTH_LONG).show()

            }else{

                mBinding!!.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_unselected))
                Toast.makeText(requireContext(),"Remove from  the favorite",Toast.LENGTH_LONG).show()

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //this is good practice
        mBinding = null
    }


}