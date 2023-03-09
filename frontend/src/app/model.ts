
    export interface Location {
        name: string;
    }

    export interface Condition {
        text: string;
    }

    export interface Current {
        lastUpdated?: any;
        temp_c: number;
        condition: Condition;
        wind_kph: number;
        humidity: number;
        cloud: number;
        feelslike_c: number;
        uv: number;
    }

    export interface Day {
        maxtemp_c: number;
        mintemp_c: number;
        avgtemp_c: number;
        maxwind_kph: number;
        totalprecip_mm: number;
        totalsnow_cm: number;
        daily_will_it_rain: number;
        daily_chance_of_rain: number;
        daily_will_it_snow: number;
        daily_chance_of_snow: number;
        uv: number;
    }

    export interface Astro {
        sunrise: string;
        sunset: string;
        moonrise: string;
        moonset: string;
        moon_phase: string;
        moon_illumination: string;
    }

    export interface Forecastday {
        date: number[];
        day: Day;
        astro: Astro;
    }

    export interface Forecast {
        forecastday: Forecastday[];
    }

    export interface WeatherData {
        location: Location;
        current: Current;
        forecast: Forecast;
    }

