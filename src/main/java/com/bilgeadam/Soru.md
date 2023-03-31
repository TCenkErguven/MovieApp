## User entity'si için gerekli katmanları ekleyiniz.
## Crud metotlarının ortak üretildiği bir yapı kurunuz.(Customer Service gibi bir ana sınıf kullanmayınız.)

## UserService de bir register metodu yazınız. --> name,surname,password,repassword
## Bu metodun gövdesini ilk olarak builder ile doldurunuz.

## Login işlemi yapılacaktır. Bunun için veritabanında kullanıcı var mı diye kontrol yapılacak ve varsa giriş yapılacaktır.Repository de veritabanındaki kayıt olan kişinin email ve password bilgisini alan bir metot yazılması gerekmektedir.

## Passwordunun uzunluğu belirlediğimiz sayıdan buyuk olanlar (2 tür Query yazılacak)
Biri natie diğeri JPQL --> native query'de @Param anotasyonunu kullanınız

## emailin sonu kullanıcının girdiği değerlere göre biten emailleri listeleyniz.