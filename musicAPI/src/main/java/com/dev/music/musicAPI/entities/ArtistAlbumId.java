package com.dev.music.musicAPI.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ArtistAlbumId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albums_id")
    private Albums albums;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artists_id")
    private Artists artists;
}
