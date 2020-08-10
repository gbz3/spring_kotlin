CREATE TABLE IF NOT EXISTS postal_code (
    code      VARCHAR NOT NULL ,
    old_zip   VARCHAR NOT NULL ,
    zip       VARCHAR NOT NULL ,
    kana_ken  VARCHAR NOT NULL ,
    kana_shi  VARCHAR NOT NULL ,
    kana_cho  VARCHAR NOT NULL ,
    kanji_ken VARCHAR NOT NULL ,
    kanji_shi VARCHAR NOT NULL ,
    kanji_cho VARCHAR NOT NULL
);
