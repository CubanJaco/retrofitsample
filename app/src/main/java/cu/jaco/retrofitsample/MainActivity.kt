package cu.jaco.retrofitsample

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import cu.jaco.retrofitsample.api.models.Post
import cu.jaco.retrofitsample.api.models.RequestError
import cu.jaco.retrofitsample.api.safeapicall.ResultWrapper
import cu.jaco.retrofitsample.di.component.ApplicationComponent
import cu.jaco.retrofitsample.preferences.Preferences
import cu.jaco.retrofitsample.ui.base.BaseActivity
import cu.jaco.retrofitsample.ui.post.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.random.Random

class MainActivity : BaseActivity(), View.OnClickListener {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PostViewModel
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            modelFactory
        ).get(PostViewModel::class.java)

        post_42.setOnClickListener(this)
        post_random.setOnClickListener(this)
        post_error.setOnClickListener(this)

    }

    override fun inject(appComponent: ApplicationComponent) {
        appComponent.inject(this)
    }

    override fun onClick(v: View?) {

        showLoading()

        when (v?.id) {

            R.id.post_42 -> {
                viewModel.getSpecificPost().observe(this, ::processResponse)
            }
            R.id.post_random -> {
                val postId = Random.nextInt(99) + 1
                viewModel.getPost("$postId").observe(this, ::processResponse)
            }
            R.id.post_error -> {
                viewModel.getPost("99999").observe(this, ::processResponse)
            }
            else -> {
                progressDialog?.dismiss()
            }

        }

    }

    private fun showLoading() {

        progressDialog?.dismiss()

        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(getString(R.string.loading))
        progressDialog?.show()

    }

    private fun processResponse(response: ResultWrapper<Post, RequestError>) {

        when (response) {

            is ResultWrapper.NetworkError -> {
                Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show()
            }

            is ResultWrapper.Success -> {
                Toast.makeText(this, response.value.title, Toast.LENGTH_SHORT).show()
            }

            is ResultWrapper.GenericError -> {
                Toast.makeText(
                    this,
                    response.error?.error_description
                        ?: "${getString(R.string.error_code)} ${response.code}"
                    , Toast.LENGTH_SHORT
                ).show()
            }

        }

        progressDialog?.dismiss()

    }

}
