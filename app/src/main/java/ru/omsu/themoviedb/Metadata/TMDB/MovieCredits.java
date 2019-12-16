package ru.omsu.themoviedb.Metadata.TMDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.omsu.themoviedb.Metadata.TMDB.Person.Actor;
import ru.omsu.themoviedb.Metadata.TMDB.Person.Crew;

public class MovieCredits {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<Actor> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Actor> getCast() {
        return cast;
    }

    public void setActor(List<Actor> actor) {
        this.cast = actor;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

}