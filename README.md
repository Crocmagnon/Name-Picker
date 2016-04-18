# Name Picker
## Requirements
This project requires __Java 8__ and uses Maven for dependencies management.

## Description
This program helps you pick a name from a list according to some conditions. An example list that comes from the [French Government Open Data](https://www.data.gouv.fr/fr/datasets/liste-des-prenoms-par-annee-prs/) is included.

### Conditions
For the moment, the conditions are :

- 6 letters. No more, no less.
- No H.
- Not ending with an A.
- Alternate vowels and consonant.
- No repeated letter.
- Exactly one B, or M, or P.

Next releases are expected to implement a GUI for defining your own conditions.
