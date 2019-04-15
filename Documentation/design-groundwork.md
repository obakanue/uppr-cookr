# Visual Design of COOKR
This is the visual design and (some) functional design that Sofi Flink and Elias Rudberg came up with through 
'research. All sources will be displayed here.

## Functionality Correlating to Design
Cooking is somewhat some part of everyones everyday life. Therefore we want to create an including app for everyone.
This is some of the sources that we will be taking into account while designing the app.

* [HaptiMap](https://moodle.lth.se/pluginfile.php/38920/mod_resource/content/1/HaptiMap%20example%20apps.pdf) on page 31,
how to make including button design for everyone. Possible to let blind people make us of the app (probably mostly the timer).   
   <p align="center"> 
     <img src="https://github.com/Jesper-Berg/AIDGrupp7/blob/master/Pictures/blind-design.png?raw=true" class="center" width="30%">
   </p>     
* [Ljud och Metod](https://moodle.lth.se/pluginfile.php/43550/mod_resource/content/1/Ljud%20o%20metod.pdf) Lecture notes on
using sounds as a way of informing the user of different actions and processes in a product. No conclusions yet, also touches on the subject above if we want to convert words to sounds.
* [Gestures](https://moodle.lth.se/pluginfile.php/43103/mod_resource/content/1/Gestures.pdf), lecture on gestures. Since it is
"A step backwards in usability" (Norman & Nielsen) we will need a tutorial or guide in the main meny easy accessible.
* [Experiences Before Things:](https://moodle.lth.se/pluginfile.php/38884/mod_resource/content/1/p2059-hassenzahl.pdf) A Primer for the (Yet) Unconvinced. We need a way to verify the user experience in the later stages of process. Since we prefer a good experience over a product. If the user has a good experience while interacting with the app the chances are higher for the user to return and use the app again. OPT: Do we want to have the possibility to take a photo of the finished course in the app? Store user data on reviews and pictures of the finished recipe? Possible to interact with other user regarding cooking. (Or we want to keep it simple and functional, where the app only becomes the background for a good experience, cooking food, is there a way to make cooking together more fun using the app?). In the western world where the materialistic world is easy accessible and affordable, experiences tend to be valued a lot higher. There is probably some correlation to how individuals in industrialized countries, with high standards of living, values self fullfillment more than anything. It is also at the same time harder for the indivudal to conceptualize what their role in this is and is probably another reason for higher rates of depression and suicide. Individuals today expect more from life, since there are an uncountable amount of options, but the road is not clear.
* [Choice is demotivating](https://www.ncbi.nlm.nih.gov/pubmed/11138768), article explains how tests shows that to many options can be discouraging for a consumer. We need to keep our product functional and easy to use.
* [Speed Matters](http://radar.oreilly.com/2009/06/bing-and-google-agree-slow-pag.html), article describes how user experience is lowered at 2 seconds delay. We need to optimize our app.

## Visual Design
This section will contain some data on why we have picked different visual design options.

### Color Theme

* [Consumer preferences for color combinations](https://www.sciencedirect.com/science/article/abs/pii/S1057740810000793), this article came up with three key findings: First, people de-emphasize lightness and focus on hue and saturation. Second, given this shift in emphasis, people generally like to combine colors that are relatively close or exactly match, with the exception that some people highlight one signature product component by using contrastive color. Third, a small palette principle is supported such that the total number of colors used in the average design was smaller than would be expected under statistical independence.   
   From this, we can draw the conclusion that the color theme needs to have a small palette with lightness, hue and saturation of colors close to each other. To make something pop in the design you can use a contrasive color.
* [Aesthetic response to color combinations:](https://link.springer.com/article/10.3758%2Fs13414-010-0027-0) preference, harmony, and similarity. Another article on the importance of similar hues in regards to preference and harmony. Though no results where shown regarding contrasive colors. Warmer colors are preferred over cooler backgrounds and vice versa. It seems though as prefered color is highly individual and does affect the result somewhat.   
   It seems that somehow the color in it self is not the most important decision to make for a developer of any product. Rather what colors to go with it and how these colors work together in harmony.
* [Button Color A/B Test:](https://blog.hubspot.com/blog/tabid/6307/bid/20566/the-button-color-a-b-test-red-beats-green.aspx?__hstc=238111519.962597c91f0224eee7e08e313944d55b.1555081310945.1555081310945.1555081310945.1&__hssc=238111519.1.1555081310946&__hsfp=2896097832) Red Beats Green. A blog presenting a test made for a particular website to observe the different traffic for different colored buttons. The red one won over the green one in traffic with 21%, the conclusion was that it standed out more than the regular green button which is a default design for most webpages.   
   This means we need a visual hierarchy for our site, this will help the user easier orientate the different menus in an app.
* [Confidence of Color Design](https://www.studiopress.com/color-design-confidence/) A small article from Studiopress about picking colors for good user design. Recommending the webpage for [Adobe Kuler](https://color.adobe.com/sv/create) which heöps you build a good pallete for your design.
* [The interactive effects of colors](https://journals-sagepub-com.ludwig.lub.lu.se/doi/10.1177/1470593106061263), article explaining how blue, green, red, white, black, and brown are precieved as cultural invariant. This seems to prove to give more opportunity for standardization in other countries (page 80).

Colorblindness is another important factor when deciding the right color for the app. Our decission is that all humans can see the color brown (if they are not blind), a color scheme with brown might be a good choice.
Brown is also a very [neutral color](https://www-emeraldinsight-com.ludwig.lub.lu.se/doi/pdfplus/10.1108/00251740610673332) (page 787), which means it won't affect the user in an unexpected way for the user experience. At the same time, hues of brown makes a product be precieved as competent in this [article](http://content.ebscohost.com/ContentServer.asp?T=P&P=AN&K=78065194&S=R&D=bth&EbscoContent=dGJyMMTo50Sep7A4v%2BbwOLCmr1Gep7FSs6e4SLaWxWXS&ContentCustomer=dGJyMPGpt0mwqrBRuePfgeyx43zx) (page 714). See below picture from same page from the same article:
<p align="center"> 
<img src="https://github.com/Jesper-Berg/AIDGrupp7/blob/master/Pictures/brown-color.png?raw=true" class="center" width="60%">
</p>
Also according to our sources brown does seem to be culturally invariant.


### Logo Design

* [The Power of White Spaces](https://www.interaction-design.org/literature/article/the-power-of-white-space) article talks about how important white space is to the design.
* [The Shape of a Logo](https://www.psychologicalscience.org/news/minds-business/the-shape-of-a-logo-has-a-powerful-impact-on-consumers120911.html) The effect a round logo has on consumers. It seems it makes consumers connect the company or product as caring, warm and sensetive to customer needs.   
   Reference: Jiang, Y., Gorn, G. J., Galli, M., & Chattopadhyay, A. (2015). Does Your Company Have the Right Logo? How and Why Circular-and Angular-Logo Shapes Influence Brand Attribute Judgments. Journal of Consumer Research. doi: 10.1093/jcr/ucv049
* [A Role of Brand Logos](https://pdfs.semanticscholar.org/d1d9/787eea6930a37e06d26b7f25b96e458ec7cc.pdf) The importance of a distinguishable and aestethic logo. This is the first encounter with the product and does fall victim to the bias of the consumer. Therefore it is important to create a good logo. The results seems to imply that brand logos with a visual symbol fair better then only a brand name. The article also mentions the importance of letting the brand logo speak to the consumer what it is about. Therefore it has to somewhat relate to the product. It also mentions that how the logo is designed (color, shape, size, etc.) does matter but was not included in the scope of the article.

The logo should use some visual symbol along with a brand name, and it would need to somewhat relate to the product, therefore we decided on the egg-timer, a common tool in different kinds of cooking methods. The name COOKR makes a unique, short therefore easily to remember product name while also relating to the product.

For this reason our Brand Name Font have own some of this charecteristicts. We went with [Abel](https://fonts.google.com/specimen/Abel), it has a clean design, some space between letters. We used capital letters since this will make the brand name highlighted and therefore more memorable. A popular font for newspapers and websites in the US with Abel being featured in more than 420 000 websites.

### COOKR App Layout

* [Fitt's Law](https://www.interaction-design.org/literature/article/fitts-s-law-the-importance-of-size-and-distance-in-ui-design), important for how to build the layout of the app. For a good user experience there have to be easily accessible buttons that do not demand a lot of accuracy.
* [When Not to Use Fitt's Law](https://www.smashingmagazine.com/2012/12/fittss-law-and-user-experience/) For a good user experience the design has to be intuitive. Always maximizing the buttons could result in some confusion since sometimes dividing the layout for different types of functions is important.
* [Eyetrack III:](https://www.poynter.org/archive/2004/eyetrack-iii-what-news-websites-look-like-through-readers-eyes/) What News Websites Look Like Through Readers’ Eyes. Explaines how bigger headlines generate less focus on information from users, the bigger the headlines tend to make users scan the site more. Regarding navigation it seemed like putting it in the top did the best. Shorter paragraphs do better than longer.
* [The Power of White Spaces](https://www.interaction-design.org/literature/article/the-power-of-white-space) article talks about how important white space is to the design.   
   The take away is that we need a balance between Fitt's Law and white space. Also that the micro white space is important for the user to easily read the content.
* [F-Shaped Pattern](https://www.nngroup.com/articles/f-shaped-pattern-reading-web-content/) Our design can affect the pattern scanning. Data suggests that it doesn't have to be F-shaped, it can be avoided through good implementation of design. The F-Shape pattern is a bad way for users to interact and read information in our app, we need to think about good design to avoid this.   
<p align="center"> 
<img src="https://github.com/Jesper-Berg/AIDGrupp7/blob/master/Pictures/f-shaped-pattern-antidote.png?raw=true" class="center" width="60%">
</p>

### COOKR App Orientation
TBC?? Do we have any sources?
