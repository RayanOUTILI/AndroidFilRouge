# ProfTracker

![logo_png](https://github.com/RayanOUTILI/AndroidFilRouge/assets/59539437/17710ef3-9cd5-4d7f-9715-5bd6406d410e)

## Préambule

Proftracker est une application de mise en relation entre enseignants et élèves, conçue pour faciliter l'accès à l'éducation et favoriser l'apprentissage personnalisé. Avec Proftracker, trouver le professeur idéal pour répondre à vos besoins académiques n'a jamais été aussi simple. Que vous soyez à la recherche d'un tuteur pour approfondir vos connaissances dans une matière spécifique, ou que vous ayez besoin d'un mentor pour vous guider dans votre parcours éducatif, notre plateforme vous met en contact avec des professionnels qualifiés et passionnés. Explorez une diversité de disciplines, choisissez le profil qui correspond le mieux à vos objectifs d'apprentissage, et lancez-vous dans une expérience éducative enrichissante et sur mesure. Bienvenue dans l'univers de Proftracker, où l'éducation devient une aventure à portée de main.

## Fonctionnalités

### Authentification

Proftracker utilise un système d’authentification lié à Firebase. Trois activités sont disponibles :

- **LoginActivity** : connexion à l'application.
  
- **RegisterActivity** : création d'un compte.
  
- **ForgottenPasswordActivity** : réinitialisation de mot de passe.

### Filtrage des professeurs

Proftracker offre la possibilité de choisir son professeur en filtrant par matière, en consultant la localisation, en filtrant les prix, etc.

### Demande de cours

Les élèves ont la possibilité de contacter le professeur de leur choix pour lui demander un cours.

### Notifications

Proftracker dispose d'un système de notifications interne à l’application. Lorsqu’un professeur se connecte, il a accès à un onglet spécifique pour consulter ses notifications. Une notification correspond à une demande de cours de la part d’un élève. Pour créer ces notifications, lorsqu’un utilisateur est connecté, il peut contacter un professeur en consultant son profil. L’action sur ce bouton déclenchera un enregistrement de la demande dans la base de données. 

### Animation

Deux animations différentes sont présentes sur l’application :

- Animation de vue après la connexion.
  
- Animation ObjectAnimator sur la RatingBar d’un professeur.

## Lien vers la vidéo démo

[![Démo ProfTracker](https://img.youtube.com/vi/ohB31Cl6fMw/0.jpg)](https://www.youtube.com/watch?v=ohB31Cl6fMw)

### Auteurs 
[Matthieu Griffonnet](https://github.com/Matt-Griff)
[Rayan Outili](https://github.com/RayanOUTILI)
[Thomas Portelette](https://github.com/ThomasPorteletteGit)


