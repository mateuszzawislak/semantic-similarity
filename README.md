Texts semantic similarity
===================

Semantic similarity of texts, how text units are close semantically (scale: 0-4).

Aim of the project
------------------
The aim of the project is to propose and implement an algorithm that will evaluate the similarity of two pieces of text (in English) on a scale from 0 to 4.
Rate 0 means that these two texts speak about two different topics.
The maximum achievable rating of 4 indicates that both texts have very similar semantically meaning.

Algorithm
------------------
The proposed method of finding semantic similarity between the two texts is based on the algorithm proposed by M. Lintean and V. Rus in the article `http://www.aaai.org/ocs/index.php/FLAIRS/FLAIRS12/paper/viewFile/4421/4801`. Rating is based on the similarity of qualitative and quantitative measure of short texts.

Requirements
------------------
Java 8

Words dictionary `http://www.linguatools.de/disco/disco-download_en.html`

Dependencies
------------------
DISCO `http://www.linguatools.de/disco/disco_en.html#api`

Stanford CoreNLP `http://nlp.stanford.edu/software/corenlp.shtml`

Configuration file
------------------
|Parameter|Type|Example value|Description
|------|------|------|------
|*disco.index.dir*|String|`C:/\dictionaries/\wiki`|directory path to dictionary used by DISCO
|*max.frequent*|Long|`3138674`|The maximum frequency of words in the dictionary observed
|*pos.accepted*|Float|`NN,JJ,NNS,VBN,VBZ,RB,VB,VBG,VBP,JJR,FW,IN`|list of linguistic forms accepted by a comma-delimited text analyzer
|*stop.words*|String|`Are,These,said`|a list of words not carrying any meaning separated by commas
|*similarity.threshold*|String|`0.01`|the minimum limit of the similarity of words over which pair of words can enter a list of word pairs connecting texts
|*rate.discretization*|String|`0.3,0.4,0.5,0.63`|boundaries of successive levels of similarity assessment discretization

Usage
------------------
`java -Dpl.edu.pw.elka.mzawisl2.semsim.config=C:/path/semsim.properties -jar semsim.jar f doc1Path s doc2Path`

Available parameters:

`f doc1Path` path to the first text document containing text

`s doc2Path` path to the second text document containing text

Example usage:

`java -Dpl.edu.pw.elka.mzawisl2.semsim.config=C:/Example/semsim.properties -jar semsim.jar f C:/Mateusz/doc1.txt s C:/Mateusz/doc2.txt`
