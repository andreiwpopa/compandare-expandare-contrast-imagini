# compandare-expandare-contrast-imagini

## Modificări neliniare de contrast prin compandarea domeniului - definită de o curbă logaritmică

Modificările neliniare de contrast prin compandarea domeniului se realizează folosind o curbă logaritmică pentru a redistribui intensitățile pixelilor într-o imagine. Aceasta poate fi utilă pentru a evidenția detalii în zonele întunecate sau luminoase ale imaginii, în funcție de preferințele artistice sau cerințele specifice ale aplicației.

Funcția matematică utilizată pentru a aplica compandarea domeniului cu o curbă logaritmică este:
![224045](https://github.com/andreiwpopa/compandare-expandare-contrast-imagini/assets/116667882/0d63d8a7-2142-4c74-b176-193b4d90084d)

Unde:
- O(x) este intensitatea pixelului rezultat
- I(x) este intensitatea pixelului inițial
- A este un parametru care controlează gradul de compresie/expansiune a domeniului intensității
- C este un parametru de scalare care poate fi utilizat pentru a ajusta nivelul de contrast general al imaginii

Valoarea lui A influențează cât de abruptă este curbă logaritmică, iar C controlează contrastul global al imaginii

Codul pentru metoda pentru care face compandarea :
![224316](https://github.com/andreiwpopa/compandare-expandare-contrast-imagini/assets/116667882/e4b8bd8e-4a59-4931-9862-c70a04c91337)


## Modificări neliniare de contrast prin expandarea domeniului - ca transformare inversă celei de compandare, deci având o alură exponențială.

Pentru a aplica modificări neliniare de contrast prin expandarea domeniului, putem folosi o alură exponențială inversă a compandării logaritmice. Astfel, vom aplica o funcție exponențială asupra intensităților pixelilor pentru a expanda domeniul intensității. Formula matematică este următoarea:
![224955](https://github.com/andreiwpopa/compandare-expandare-contrast-imagini/assets/116667882/7a796ab4-82d5-4ef8-8497-a4dd8ee53c54)

Codul pentru metoda pentru care face compandarea :
![225113](https://github.com/andreiwpopa/compandare-expandare-contrast-imagini/assets/116667882/1cfdaf10-2847-4355-81ae-86d3c0643951)

O metoda unde verific daca imaginea introdusa este pe 8 sau 24 biti: 
![225332](https://github.com/andreiwpopa/compandare-expandare-contrast-imagini/assets/116667882/1a824e50-12ed-4f8b-986f-20bf0dd97a47)

Interfata grafica: 
![225428](https://github.com/andreiwpopa/compandare-expandare-contrast-imagini/assets/116667882/92195217-aeb5-4090-b3e8-7c36999a5ba3)








