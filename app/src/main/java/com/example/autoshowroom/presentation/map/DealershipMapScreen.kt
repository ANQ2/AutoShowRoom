package com.example.autoshowroom.presentation.map

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.fillMaxSize

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DealershipMapScreen() {
    val htmlData = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <title>Автосалоны</title>
            <script src="https://api-maps.yandex.ru/2.1/?apikey=38fc108d-832e-43e1-85ff-49bf57f9780e&lang=ru_RU"
                type="text/javascript"></script>
            <style>
                html, body, #map {
                    width: 100%; height: 100%; padding: 0; margin: 0;
                }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script type="text/javascript">
                ymaps.ready(function () {
                    var map = new ymaps.Map("map", {
                        center: [43.238949, 76.889709],
                        zoom: 12,
                        controls: ['zoomControl', 'searchControl']
                    });

                    var points = [
                        {
                            coords: [43.238949, 76.889709],
                            name: "AutoDealer Алматы"
                        },
                        {
                            coords: [51.1605, 71.4704],
                            name: "AutoDealer Астана"
                        }
                    ];

                    points.forEach(function (point) {
                        var placemark = new ymaps.Placemark(point.coords, {
                            balloonContent: point.name
                        });
                        map.geoObjects.add(placemark);
                    });
                });
            </script>
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
            }
        }
    )
}
