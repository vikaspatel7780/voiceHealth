package com.myapp.voicehealth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.myapp.voicehealth.ui.theme.VoiceHealthTheme
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapp.voicehealth.R
import com.myapp.voicehealth.core.storage.UserPreferences
import com.myapp.voicehealth.ui.components.FeatureTile
import com.myapp.voicehealth.ui.components.VoiceHealthTopBar
import com.myapp.voicehealth.ui.theme.PrimaryBlue
import com.myapp.voicehealth.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val userPrefs = UserPreferences(LocalContext.current)
    val authViewModel: AuthViewModel = viewModel()
    val bannerImages = listOf(
        R.drawable.doctor_banner1,
        R.drawable.doctor_banner2,
        R.drawable.doctor_banner1,
        R.drawable.doctor_banner3
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController,
                onItemClick = { navigationText ->
                    navController.navigate(navigationText)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                VoiceHealthTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onUpgradeClick = { /* Handle Upgrade */ }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Health Tip + Banner
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        val healthTips = listOf(
                            "💡 Stay hydrated and sleep well.",
                            "💡 Visit your doctor regularly.",
                            "💡 Complete all your medications.",
                            "💡 A 30-min walk keeps your heart healthy."
                        )
                        val healthTip = remember { healthTips.random() }

                        Text(
                            text = healthTip,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF444444),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        AutoScrollingBanner(images = bannerImages)

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item {
                    // Grid tiles
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth() // ✅ Use fillMaxWidth, NOT fillMaxHeight
                            .heightIn(max = 650.dp) // ✅ Give it a bounded height
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        content = {
                            item {
                                FeatureTile("Ask AI Assistant", R.drawable.ai_assist) {
                                    navController.navigate("ask_ai_screen")
                                }
                            }
                            item {
                                FeatureTile("Doctor Suggestions", R.drawable.doctors_advice) {
                                    navController.navigate("doctor_suggestion")
                                }
                            }
                            item {
                                FeatureTile("Scan Report", R.drawable.scan_report) {
                                    navController.navigate("scan_report_screen")
                                }
                            }
                            item {
                                FeatureTile("Health Records", R.drawable.health_record) {
                                    navController.navigate("health_history_screen")
                                }
                            }
                            item {
                                FeatureTile("Nearby Doctors", R.drawable.nearby_doctors) {
                                    navController.navigate("nearby_doctors_screen")
                                }
                            }
                            item {
                                FeatureTile("Book Appointment", R.drawable.appointment_book_1) {
                                    navController.navigate("nearby_doctors_screen")
                                }
                              
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    
                }
            }
        }
    }
}







@Composable
fun DrawerContent(
    navController: NavController,
    userName: String = "John Doe",
    userEmail: String = "john.doe@example.com",
    onItemClick: (String) -> Unit // Pass route or label
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPrefs = UserPreferences(context)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Profile section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_user),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(userName, style = MaterialTheme.typography.titleMedium)
                Text(userEmail, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Divider()

        // Menu items
        DrawerMenuItem(text = "Home", icon = Icons.Default.Home) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Doctor Suggestions", icon = Icons.Sharp.AccountBox) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Search Doctors", icon = Icons.Default.Search) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Health History", icon = Icons.Default.Done) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Profile Settings", icon = Icons.Default.Settings) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Notifications", icon = Icons.Default.Notifications) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Help & Feedback", icon = Icons.Default.Info) {
            onItemClick("doctor_suggestion")
        }

        Spacer(modifier = Modifier.weight(1f))

        Divider()

        DrawerMenuItem(text = "Logout", icon = Icons.Default.ExitToApp) {
            scope.launch {
                userPrefs.clearUser() // Clear token & login flag
                navController.navigate("login_screen") {
                    popUpTo(0) { inclusive = true } // Clear back stack
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
fun DrawerMenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AutoScrollingBanner(
    images: List<Int>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % images.size
            listState.animateScrollToItem(currentIndex)
        }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val bannerHeight = screenWidth * 0.5f // 16:9 or 2:1 ratio

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .height(bannerHeight),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(images.size) { index ->
            Box(
                modifier = Modifier
                    .width(screenWidth - 40.dp) // to add spacing and padding
                    .height(bannerHeight)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = "Banner $index",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )

                // Ad badge (top-left)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color(0xAA000000), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Ad",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Bottom overlay text
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Book a free doctor consultation today",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


