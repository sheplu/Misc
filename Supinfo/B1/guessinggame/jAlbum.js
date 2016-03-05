
// Initialisation des différentes variables
nb_load_image = 0;
Count_Image_Aff = new Array;
GridTab = new Array;
Validator = 0;
Recording = new Array;
etat1 = new Image;
etat1.src = 'images/QuestionMark.jpeg';
etat2 = new Image;


// Placement aléatoire des images
function Begin_Game()
{
	array2.src = "images/Image1.jpeg";
		Count_Image();
	array2.src = "images/Image2.jpeg";
		Count_Image();
	array2.src = "images/Image3.jpeg";
		Count_Image();
	array2.src = "images/Image4.jpeg";
		Count_Image();
	array2.src = "images/Image5.jpeg";
		Count_Image();
	array2.src = "images/Image6.jpeg";
		Count_Image();
	array2.src = "images/Image7.jpeg";
		Count_Image();
	array2.src = "images/Image8.jpeg";
		Count_Image();

Random();
	Random('img8',8);
	Random('img9',9);
	Random('img10',10);
	Random('img11',11);
	Random('img18',18);
	Random('img19',19);
	Random('img20',20);
	Random('img21',21);
	Random('img28',28);
	Random('img29',29);
	Random('img30',30);
	Random('img31',31);
	Random('img38',38);
	Random('img39',39);
	Random('img40',40);
	Random('img41',41);
}

function Count_Image()
	{
		GridTab[nb_load_image] = array2.src;
		Count_Image_Aff[nb_load_image] = 0;
		Count_Image_Aff[nb_load_image+8] = 0;
		nb_load_image++;
		return(true)
	}


function Random(Name,Nombre)
	{
		ImgTemp = Math.round(Math.random() * 7);
		while (Count_Image_Aff[ImgTemp]>=2)
			{
				ImgTemp = Math.round(Math.random() * 7);
			}
		Recording[ImgNb] = GridTab[ImgTemp];
		Count_Image_Aff[ImgTemp]++;
		return(true);
	}

count = 0;
gagne=0;


function AddClic(ImgNb,Name)
	{
		if (Validator == 0)
		{
			if (Name.src != etat.src)
			{
				alert('Cette images est déja retournée');
			}
			else
			{
				Name.src = Recording[ImgNb];
				count++;
				Namey = Name;
				if (count == 1)
				{
					ImgNb1=ImgNb;
					Namex=Namey;
					ImgURL1=Recording[ImgNb];
				}
				else
				{
					Validator=1;
					if (Recording[ImgNb] == ImgURL1)
						{
							gagne++;
							Validator = 0;
							count=0;
						if (gagne==12)
							{
								alert('Congratulation, You win!!!!');
							}
						}
					else
						{
							alert('Sorry, but it is not a peer!');
						}
				}
			}
		}
	}
	
ThisPage = location.href;