package com.example.pingme.SplashScreen

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavController
import com.example.pingme.JWT_Token.TokenManager
import com.example.pingme.ReminderManagement.View.extractUsernameFromToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pingme.Navigation.isTokenExpired
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(navController: NavController, context: Context) {
    // State for animations
    val logoScale = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val textScale = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val ringProgress = remember { Animatable(0f) }

    // App theme colors
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val accentColor = MaterialTheme.colorScheme.tertiary

    // Animated pulse effect
    val infiniteTransition = rememberInfiniteTransition()
    val pulse = infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Particle effects
    val particles = List(30) { index ->
        remember {
            Animatable(0f)
        }
    }

    // Animation sequence
    LaunchedEffect(Unit) {
        // Logo appearance
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(700, easing = FastOutSlowInEasing)
            )
        }
        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(500)
            )
        }

        // Ring animation
        delay(200L)
        launch {
            ringProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(1500, easing = FastOutSlowInEasing)
            )
        }

        // Text appearance
        delay(600L)
        launch {
            textScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(700, easing = FastOutSlowInEasing)
            )
        }
        launch {
            textAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(700)
            )
        }

        // Animate particles
        particles.forEachIndexed { index, animatable ->
            launch {
                delay(800L + (index * 15L))
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 1200 + (index * 20),
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }

        // Check auth token and navigate after animations
        delay(2800L)
        val token = TokenManager.getToken(context)
        if (token != null && !isTokenExpired(token)) {
            val username = extractUsernameFromToken(token)
            navController.navigate("homeScreen/$username") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("signup") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // UI while waiting for token check
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        // Particles
        particles.forEachIndexed { index, animatable ->
            val size = (5 + (index % 5)).dp
            val angle = index * 12f
            val distance = 120 + (index % 5) * 40
            val xOffset = cos(Math.toRadians(angle.toDouble())).toFloat() * distance
            val yOffset = sin(Math.toRadians(angle.toDouble())).toFloat() * distance

            Box(
                modifier = Modifier
                    .offset(x = xOffset.dp, y = yOffset.dp)
                    .size(size)
                    .scale(1f - (animatable.value * 0.8f))
                    .alpha(animatable.value * 0.7f)
                    .background(
                        color = when {
                            index % 3 == 0 -> primaryColor
                            index % 3 == 1 -> secondaryColor
                            else -> accentColor
                        },
                        shape = CircleShape
                    )
            )
        }

        // Animated content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Logo with rings
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value)
            ) {
                // Animated rings
                Canvas(
                    modifier = Modifier
                        .size(200.dp)
                        .scale(pulse.value)
                ) {
                    // Outer ring
                    drawArc(
                        color = primaryColor,
                        startAngle = -90f,
                        sweepAngle = 360f * ringProgress.value,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx()),
                        size = Size(size.width, size.height)
                    )

                    // Middle ring
                    drawArc(
                        color = secondaryColor,
                        startAngle = 45f,
                        sweepAngle = 360f * ringProgress.value,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx()),
                        size = Size(size.width * 0.75f, size.height * 0.75f),
                        topLeft = Offset(size.width * 0.125f, size.height * 0.125f)
                    )

                    // Inner ring
                    drawArc(
                        color = accentColor,
                        startAngle = 180f,
                        sweepAngle = 360f * ringProgress.value,
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx()),
                        size = Size(size.width * 0.5f, size.height * 0.5f),
                        topLeft = Offset(size.width * 0.25f, size.height * 0.25f)
                    )
                }

                // Central logo with shadow effect
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                        .scale(pulse.value)
                        .graphicsLayer {
                            shadowElevation = 12f
                            shape = CircleShape
                        }
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Chat,
                        contentDescription = "Ping Me Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Animated app name
            Text(
                text = "Ping Me",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .scale(textScale.value)
                    .alpha(textAlpha.value)
                    .graphicsLayer {
                        shadowElevation = 8f
                    }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline with delayed appearance
            Text(
                text = "Stay Connected, Always",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .scale(textScale.value)
                    .alpha(textAlpha.value * 0.8f)
            )
        }
    }
}