package com.myapp.voicehealth.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myapp.voicehealth.domain.models.Message
import com.myapp.voicehealth.ui.components.BottomInputBar
import com.myapp.voicehealth.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AskAIScreen(navController: NavController) {
    val messages = remember { mutableStateListOf<Message>() }
    val lastUserMessage = messages.lastOrNull { it.isUser }

    LaunchedEffect(lastUserMessage) {
        lastUserMessage?.let {
            delay(800L)
            val response = when (it.text.lowercase().trim()) {
                "hello", "hi", "hey" -> "Hello! 👋 How can I assist you with your health today?"
                else -> "Thank you for your question. Please consult a doctor for detailed advice."
            }
            messages.add(Message(response, isUser = false))
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ai Suggestion") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomInputBar { text ->
                if (text.isNotBlank()) {
                    messages.add(Message(text, isUser = true))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (messages.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "I’m your health assistant.\nWhat would you like help with today?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages) { message ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
                        ) {
                            Text(
                                text = message.text,
                                color = Color.White,
                                modifier = Modifier
                                    .background(
                                        if (message.isUser) PrimaryBlue else Color.DarkGray,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

