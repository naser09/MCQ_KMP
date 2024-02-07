package presentaion.registration_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.use_cases.GetDataFromInternet
import io.ktor.util.date.GMTDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class RegistrationScreen:Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val getDataFromInternet = koinInject<GetDataFromInternet>()
        val navigator = LocalNavigator.currentOrThrow
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var about by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf(0) }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Image(
                    painter = painterResource(res = "compose-multiplatform.xml"), // Replace with your logo
                    contentDescription = "App logo",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
                val datePickerState = rememberDatePickerState()
                var showDatePicker by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var dateOfBirth by remember { mutableStateOf("") }
                    Text("Date of Birth:$dateOfBirth")
                    Button(onClick = {showDatePicker = showDatePicker.not()}){
                        Text("Select Date of Birth")
                    }
                    AnimatedVisibility(showDatePicker){
                        DatePickerDialog(modifier = Modifier,
                            confirmButton = {
                                Button(onClick = {
                                    datePickerState.selectedDateMillis?.let {
                                        val d = GMTDate(timestamp = it)
                                        dateOfBirth = " - ${d.dayOfMonth} - ${d.month} - ${d.year}"
                                    }
                                }){
                                    Text("Confirm")
                                }
                            }, onDismissRequest = {
                                                  showDatePicker = showDatePicker.not()
                            }, content = {
                                DatePicker(datePickerState)
                            })
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Gender:")
                    RadioGroup(
                        selected = gender,
                        onSelectedChange = { gender = it },
                        choices = listOf(
                            0 to "Male",
                            1 to "Female"
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = about,
                    onValueChange = { about = it },
                    label = { Text("About Me") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                var data by remember { mutableStateOf("") }
                Text(data)
                LaunchedEffect(Unit){
                    data = getDataFromInternet.getKtorDocs()
                }
                Button(
                    onClick = {
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Already have an account? Login",
                    modifier = Modifier.clickable { navigator.pop() },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    @Composable
    private fun RadioGroup(
        selected: Int,
        onSelectedChange: (Int) -> Unit,
        choices: List<Pair<Int, String>>
    ) {
        Column {
            choices.forEach { (value, text) ->
                Row(
                    modifier = Modifier.clickable { onSelectedChange(value) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RadioButton(
                        selected = value == selected,
                        onClick = null // Not using default click as we handle it manually
                    )
                    Text(text)
                }
            }
        }
    }
}