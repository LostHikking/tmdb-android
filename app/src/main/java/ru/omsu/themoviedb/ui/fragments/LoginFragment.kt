package ru.omsu.themoviedb.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import ru.omsu.themoviedb.R
import ru.omsu.themoviedb.recommendation.RecService
import ru.omsu.themoviedb.recommendation.UserDTO
import java.nio.charset.Charset


class LoginFragment : Fragment() {
    private lateinit var recService: RecService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val sp = PreferenceManager.getDefaultSharedPreferences(this.context)
        recService = RecService.create()
        var login = sp.getString("login", null)
        var password = sp.getString("password", null)
        return if (login == null && password == null) {
            val loginButton = view.findViewById<View>(R.id.loginButton) as Button
            loginButton.setOnClickListener {
                login = (view.findViewById<View>(R.id.login) as EditText).text.toString()
                password = (view.findViewById<View>(R.id.login) as EditText).text.toString()
                val userDTO = connect(login!!, password!!, sp)
                if (userDTO != null) {
                    val fm: FragmentManager = parentFragmentManager
                    fm.beginTransaction().replace(R.id.fragment_container, RecommendationsFragment(userDTO)).commit()
                }
            }
            setHasOptionsMenu(false)
            view
        } else {
            val userDTO = connect(login!!, password!!, sp)
            if (userDTO != null) {
                val fm: FragmentManager = parentFragmentManager
                fm.beginTransaction().replace(R.id.fragment_container, RecommendationsFragment(userDTO)).commit()
            }
            view
        }
    }

    private fun Pair<String, String>.toAuthorization(): String {
        return "Basic " + encodeToString(("$first:$second").toByteArray(Charset.defaultCharset()), NO_WRAP)
    }

    private fun connect(login: String, password: String, sp: SharedPreferences): UserDTO? {
        var userDTO: UserDTO? = null
        val thread = Thread {
            val response = recService.getUserInfo((login to password).toAuthorization()).execute()
            if (!response.isSuccessful) {
                activity?.runOnUiThread {
                    Toast.makeText(this.context, "Проверьте логин и пароль", Toast.LENGTH_LONG).show()
                }
            } else {
                sp.edit().putString("login", login).apply()
                sp.edit().putString("password", password).apply()
                userDTO = response.body()
            }
        }
        thread.start()
        thread.join()
        return userDTO
    }
}