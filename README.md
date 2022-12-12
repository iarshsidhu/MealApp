<h1 align="center">Meal App</h1>

<p align="center">
<img src="https://user-images.githubusercontent.com/54114888/133693270-5a94c55f-c731-4408-b120-e76754ea7a4c.jpg" width="" height="">
</p>

## 📜 Description:
Developed the "Meal App" Android application to show a list of Dishes with photos, names and gives full information about the selected meal fetched from a remote API from www.themealdb.com. Used MVVM Architecture and Kotlin as programming language.

### 🔗 Link for website: https://www.themealdb.com/

# Preview
<img src="https://user-images.githubusercontent.com/97103840/207003698-ed7f1dd3-92a5-4eb9-9124-f9cf2d2eb95c.jpg" width="300" height="600">

<img src="https://user-images.githubusercontent.com/97103840/207003974-135af03c-ee8c-4fe5-9e57-e634553f44c5.jpg" width="300" height="600">

<img src="https://user-images.githubusercontent.com/97103840/207004043-a8494857-4443-45c5-9552-fbca3269573f.jpg" width="300" height="600">

https://user-images.githubusercontent.com/97103840/207015150-2b8ea59f-2ecd-4ef1-ac38-7150d701745c.mp4

# Libraries and technologies used
- Navigation Component : One activity contains multiple fragments instead of creating multiple activites.
- Retrofit : Making HTTP connection with the rest API and convert meal json file to Kotlin/Java object.
- Room : Save meals in local database.
- MVVM & LiveData : Separate logic code from views and save the state in case the screen configuration changes.
- Coroutines : Do some code in the background.
- View Binding : Instead of inflating views manually view binding will take care of that.
- Glide : Catch images and load them in ImageView.
