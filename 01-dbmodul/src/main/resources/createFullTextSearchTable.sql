-- Erzeugung einer Textbasierten Suchtabelle, Beispiel:
CREATE VIRTUAL TABLE DefekteSearch USING fts4(id, Artikelbezeichnung, AuftragsNr, LieferantName);
INSERT INTO DefekteSearch SELECT RecId, Artikelbezeichnung, AuftragsNr, LieferantName FROM defekte;
select * from DefekteSearch where DefekteSearch match 'clexane aca muell*';
DROP TABLE DefekteSearch;