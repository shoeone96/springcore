package com.sparta.springcore.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

// 추가적으로 이 validator를 설정함으로 인해 이게 유효한지를 확인하는 작업도 필
public class URLValidator {
    public static boolean urlValidator(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (URISyntaxException exception) {
            return false;
        }
        catch (MalformedURLException exception) {
            return false;
        }
    }
}