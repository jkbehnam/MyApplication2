package com.example.behnam.myapplication;

import com.example.behnam.myapplication.objects.Drug;

import java.util.ArrayList;


public class druges {
    static private druges instance;
    private ArrayList<Drug> d_list = new ArrayList<>();

    public druges() {
        Drug d;
        {
            d = new Drug();
            d.setDrug_code("Antiplatelet");
            d.setDrug_name("عوامل ضد پلاکت");
            d.setDrug_describe("داروهای این دسته کاهش دهنده واقعی خطر هستند و مهمترین قرص هایی که بعد از حمله قلبی مصرف می کنید این داروها هستند.\n" +
                    "آسپرین معمول ترین دارویی است که باعث کاهش چسبندگی پلاکت های خونی می شود. این دارو احتمال تشکیل لخته های خونی را کاهش و بیماری های شریانی را متوقف می کند.\n" +
                    "خوردن یک آسپرین در روز خطر حمله قلبی یا سکته را بطور قابل توجهی کاهش می دهد. این اثر در مقدار کم دارو  پایین اتفاق می افتد. عوارض جانبی این دارو در مقادیر پایین نادر است، مگر در افرادی که مشکل معده دارند.\n" +
                    "•\tمیزان مصرف آسپرین 80 میلی\u200Cگرمی را زیاد نکنید زیرا مزیتی ندارد.آسپرین 80 روکش دار نباید شکسته شده یا جویده شود.\n" +
                    "•\tاگر با خوردن آسپرین دچارآلرژی یا مشکلاتی مثل آسم می شوید ، داروی جایگزین مثل کلوپیدوگرل به شما توصیه می شود. بیشتر اوقات کلوپیدوگرول به همراه آسپرین بعد از حمله قلبی استفاده می شود.\n" +
                    "نمونه های عوامل ضد پلاکت :\n" +
                    "1-\tکلوپیدوگرول \n" +
                    "2-\tآسپرین \n" +
                    "•\tتوصیه می شود آسپرین همزمان با بروفن مصرف نشود.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("Bata-blockers");
            d.setDrug_name("بتابلوکرها");
            d.setDrug_describe("این دارو ها کاهنده\u200Cهای مشکلات قلبی و عروقیخصوصا در طی سال اول بعد از حمله قلبی هستند. آنها همچنین برای کنترل علائمی مثل آنژین استفاده می شوند. این دارو ها اثرات آدرنالین را متوقف می کنند و ضربان قلبرا کاهش می دهند.\n" +
                    "اسامی شیمیایی بتا بلوکرها معمولا به الول(olol) ختم می شود. مثل: \n" +
                    "1-\tآتنولول \n" +
                    "2-\tبیزوپرولول\n" +
                    "3-\tمترپرولول\n" +
                    "4-\tکارودیلول\n" +
                    "عوارض جانبی آنها می تواند شامل : خستگی، سردی انگشتان دست و پا، وز وز گوش، کابوس های شبانه و مشکلات جنسی باشد(در بیماران دچار آسم معمولا نباید یا با احتیاط فراوان استفاده شوند). بیشتر این عوارض را می توان با کاهش دوز دارو یا تغییر نوع بتابلوکر، البته بعد از مشورت با پزشک معالج برطرف نمود.\n" +
                    "•\tنباید مصرف بتابلوکر هارا به طور ناگهانی متوقف نمود،چرا که باعث آنژین شدید می شود.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("Statins");
            d.setDrug_name("استاتین ها");
            d.setDrug_describe("همانطور که می دانید کلسترول بالا یک فاکتور خطر برای بیماری های قلبی عروقی است. در اکثر بیماران، کاهش سطح کلسترول خون احتمال حمله قلبی مجدد را کاهش می دهد. استاتین ها از دیگر کاهش دهنده های خطر هستند.\n" +
                    " این داروها همچنین باعث کاهش التهاب عروق می شوند که اثری غیر وابسته به اثر کاهش دهنده کلسترول خون است. \n" +
                    "علاوه بر این استاتین ها موجب کاهش سنتز کلسترول در کبد شما می شوند.\n" +
                    "نمونه هایی از استاتین ها: \n" +
                    "1-\tسیمو استاتین \n" +
                    "2-\tآتورواستاتین\n" +
                    "3-\tرزوواستایتن\n" +
                    "این دارو ها خیلی سریع دست به کار می شوند و طی چند هفته خطر را کاهش می دهند.\n" +
                    "عوارض جانبی آنها می تواند شامل خستگی، درد عضلانی، ناراحتی معده و سردرد باشد.\n" +
                    "•\tآزمایشات خونی بایستی در جهت بررسی اثر دارو روی سطح کلسترول شما انجام شود.گهگاه این دارو ها می توانند باعث التهاب در عضلات یا کبد شوند.این اثر ممکن است در آزمایشات خونی مشخص شود. که بایستی نوع دارو را عوض کرد.\n" +
                    "•\tهرگونه درد عضلانی را فورا به پزشکتان گزارش دهید، چرا که ممکن است اولین نشانه از یک عارضه جانبی جدی باشد.\n" +
                    "•\tچنانچه شما قادر به مصرف استاتین ها نیستید یا اینکه آنها برای شما مفید نیستند، پزشکتان ممکن است دیگر داروهای کاهش دهنده کلسترول مثل ازتیمایب را برایتان تجویز کند.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("ACE & ARB's");
            d.setDrug_name("مهار کننده های آنزیم تبدیل کننده آنژیوتانسین و بلاک کننده های گیرنده آنژیوتانسین  ");
            d.setDrug_describe("این نوع داروها  نیز می توانند خطر حمله قلبی را کاهش دهند.این دارو ها عروق خونی را گشاد می کنند و موجب کاهش فشار خون می شوند.\n" +
                    "بعد از یک حمله قلبی آنها می توانند عمل پمپاژ قلب را بهبود بخشند. این کار به رفع خستگی و نفس نفس زدن و به طور ساده به رفع فرایند صدمه کمک می کنند.\n" +
                    "اسامی شیمیایی مهار کننده های ACE  معمولا به پریل(pril ) ختم می شود. نمونه هایی از مهار کننده های ACE:\n" +
                    "1-\tلیزینوپریل \n" +
                    "2-\tکاپتوپریل\n" +
                    "3-\tانالاپریل\n" +
                    "از عوارض جانبی آنها میتوان به سرگیجه هنگام ایستادن ، سرفه خشک  و اختلال در حس چشایی اشاره کرد.\n" +
                    "اگر شما قادر به مصرف مهارکننده تبدیل کننده آنژیوتانسین نیستید، بایستی مهارکننده گیرنده آنژیوتانسین استفاده کنید.\n" +
                    "اسامی شیمیایی بلاک کننده های رسپتور آنژیوتانسین معمولا به زارتان ختم می شود. شامل:\n" +
                    "1-\tلوزارتان \n" +
                    "2-\tوالزارتان.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("Nitrates");
            d.setDrug_name("نیترات ها");
            d.setDrug_describe("•\tاین دارو ها برای کنترل آنژین استفاده می شوند.\n" +
                    "•\tنیترات ها موجب گشاد شدن عروق خونی\u200Cای که خون را به قلب می برند می شوند.این عمل باعث کاهش مقدار خون برگشتی به قلب می شود و در نتیجه قلب کار کمتری انجام می دهد.\n" +
                    "•\tهمچنین نیترات ها ممکن است بویژه شریان کرونری را گشاد کنند که ارسال خون به عضله قلبی را بهبود می بخشد.\n" +
                    "هنگام ترک بیمارستان ممکن است برای شما یک پرل(قرص ژله ای مانند) نیتروگلیسرین زیرزبانی تجویز شود که بایستی در هنگام درد قفسه سینه، زیر زبانتان قرار دهید. درصورت نیاز به پرل زیرزبانی بایستی دستور مصرف آن نیز برای شما توضیح داده شود.\n" +
                    "توصیه معمول این است که:\n" +
                    "•\tدرصورت درد قفسه سینه پرل را زیر زبانتان قرار دهید و به مدت 5 دقیقه صبر کنید\n" +
                    "•\tاگر هنوز درد دارید ، مقدار را تکرار کنید و ۵ دقیقه دیگر صبر کنید. \n" +
                    "•\tچنانچه دردتان همچنان ادامه داشت ، یکبار دیگر هم می توانید این کار را انجام دهید.\n" +
                    "•\tاگر درد شما غیر قابل تحمل بود یا اگر بعد از ۱۵ دقیقه همچنان درد شما ادامه داشت یا اگر درد در طول این زمان بیشتر و بدتر شد با شماره 115 یا شماره اورژانس محلتان تماس بگیرید و درخواست آمبولانس دهید و به آنها بگویید که به حمله قلبی مشکوک هستید.\n" +
                    "نیتراتها داروهای حساس به نور، دما و رطوبت هستند. لازم است این داروها در ظروف عایق به نور، دور از رطوبت و گرما نگهداری شوند\n" +
                    "اگر آنژین دارید ، ممکن است برای پیشگیری از حمله مجدد قلبی ،نیترو\u200Cگلیسرین تجویز شود. \n" +
                    "انواع دیگری از نیتروگلیسرین وجود دارد که شامل قرص های کوتاه اثر  و بلند اثر هستند. مانند :\n" +
                    "1.\tایزوسورباید دی نیترات \n" +
                    "2.\tایزوسورباید مونو نیترات \n" +
                    "3.\tقرص های نیتروگلیسرین یا نیتروکانتین\n" +
                    "4.\tپماد موضعی نیتروگلیسرین\n" +
                    "عوارض جانبی آنها می تواند شامل سرگیجه هنگام ایستادن و سردرد باشد.البته سردرد بعد از چند هفته از شروع دارو از بین می رود.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("Calcium channel blockers");
            d.setDrug_name("بلوک کننده های کانال کلسیم");
            d.setDrug_describe("این دارو ها برای درمان آنژین یا فشار خون بالا و گاهی برای تپش قلب یا اختلالات ریتمیک قلبی استفاده می شوند. این داروها نیز مانند نیترات ها، عروق خونی را گشاد می کنند. آنها پمپاژ خون و  فشار خون را کاهش می دهند. همچنین شریانهای کرونری را گشاد می کنند که منجر می شود به افزایش جریان خون عضلات قلب. در بعضی موارد این داروها عضله قلب را آرام کرده و ضربان قلب را آهسته می کنند.\n" +
                    "نمونه هایی از مهارکننده کننده های کانال کلسیم :\n" +
                    "1.\tآملودیپین \n" +
                    "2.\tدیلتیازم \n" +
                    "3.\tوراپامیل\n" +
                    "عوارض جانبی این دارو ها نیز شامل برافروختگی صورت، حالت تهوع، ورم مچ پا، خونریزی لثه، یبوست و سردرد می باشد.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("Potassium-channel activators");
            d.setDrug_name("فعال کننده های کانال پتاسیم");
            d.setDrug_describe("این دارو ها نیز برای درمان آنژین بکار می روند. آنها شریان ها و ورید ها را گشاد میکنند و بار قلب را کاهش می دهند. همچنین عروق کوچک قلب را گشاد می کنند و در نتیجه به بهبود ارسال خون به عضله قلب کمک می کنند.\n" +
                    "نمونه ای از این گروه:\n" +
                    "•\tنیکوراندیل \n" +
                    "از عوارض جانبی میتوان به سرگیجه در حالت ایستاده، سردرد، حالت تهوع و بر افروختگی صورت اشاره کرد.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("مدرها");
            d.setDrug_name("دیورتیک ها(مدرها)");
            d.setDrug_describe("این داروها برای دفع آب اضافی تولید شده در بدن بعد از یک حمله قلبی که می\u200Cتواند منجر به تورم مچ پا یا نفس نفس زدن شود، استفاده می شوند. بعضی از مدرها برای درمان فشار خون بالا استفاده می شوند. درواقع مدرها، روی کلیه های شما و تولید ادرار بیشتر اثر می گذارند.\n" +
                    "نمونه هایی از این گروه : \n" +
                    "1.\tفوروزماید \n" +
                    "2.\tهیدروکلروتیازید\n" +
                    "3.\tمتولازون\n" +
                    "4.\tتریامترن - H\n" +
                    "عوارض جانبی این دارو ها شامل احساس خستگی یا ضعف و مشکلات جنسی است .\n" +
                    "شما بایستی مصرف غذاهای شور مثل  پنیر نمکی، چیپس، بادام زمینی نمکی و ...کاهش دهید. چراکه این ها تولید مایع در بدن را افزایش می دهند.\n" +
                    "از آنجایی که دیورتیک ها کار کلیه ها را بیشتر می کنند، در طی مصرف این داروها بایستی گهگاهی تست های خونی جهت بررسی عملکرد آن ها زیر نظر پزشک انجام داد.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("");
            d.setDrug_name("آنتی اریتمی ها ");
            d.setDrug_describe("بعضی بیماران برای درمان اختلالات ریتمیک قلبی به دارو نیاز دارند.این دارو ها به عنوان آنتی\u200Cآریتمی شناخته می شوند. متخصص قلب شما در مورد مدت زمان مصرف این دارو ها راهنمایی میکند.\n" +
                    "نمونه هایی از ضد آریتمی ها شامل : \n" +
                    "1.\tدیگوکسین \n" +
                    "2.\tآمیودارون \n" +
                    "3.\tوراپامیل\n" +
                    " این دارو ها بسیار باهم تفاوت دارند، بنابراین حتما بروشور اطلاعاتی آنها برای چک کردن عوارض جانبی را مطالعه کنید.\n" +
                    "ضد انعقاد هایی مانند وارفارین، جهت رقیق کردن خون استفاده می شوند بنابراین خون به راحتی منعقد نمی شود.\n" +
                    "درصورت داشتن لخته های خونی در ورید ها یا داخل قلب(بویژه اگر مشکلاتی در ریتم قلب بلافاصله بعد از حمله قلبی داشتید)، داروهای ضد انعقاد برای درمان کوتاه مدت بعد از حمله قلبی بایستی تجویز شوند.\n" +
                    "درصورت داشتن مشکلات مداوم در ریتم قلب یا حجم داخل قلب، نیاز به مصرف مادام\u200Cالعمر ضدانعقاد ها می باشد.\n" +
                    "عوارض جانبی وارفارین می\u200Cتواند خطرناک باشد،که به علت رقیق شدن بیش از اندازه خون می باشد و منجر به خون مردگی زیر پوستی یا خونریزی می شود.\n" +
                    "چنانچه وارفارین استفاده می\u200Cکنید، ممکن است متوجه ظهور سریع کبودی یا خونریزی طولانی شده باشید. ولی درصورت بدتر شدن ناگهانی این علائم بایستی با پزشک خود تماس بگیرید.در طول مصرف این دارو باید آزمایشات منظم خون انجام شود تا از مقدار صحیح دارو مطمئن شویم و درصورت نیاز به مصرف داروی دیگر همزمان با وارفارین ممکن است نیاز به تنظیم مقدار وارفارین باشد .\n" +
                    "قبل از خوردن انواع داروهای بدون نسخه(OTC) مثل داروهای گیاهی، حتما با پزشک یا داروساز مشورت کنید.چراکه تقریبا تمام ااین ها با وارفارین تداخل اثر دارند.\n");
            getD_list().add(d);
        }
        {
            d = new Drug();
            d.setDrug_code("مکمل های روغن ماهی");
            d.setDrug_name("مکمل های روغن ماهی");
            d.setDrug_describe("مکمل\u200Cهای روغن ماهی در افراد بعد از حمله قلبی که قادر به استفاده مرتب از ماهی در رژیم غذایی خود نیستند، توصیه می شود. همچنین کپسول های روغن ماهی نیز در دسترس     می باشد.  • این مکمل\u200Cها در افرادی که ضدانعقادها(مانند وارفارین) و ضدپلاکت ها (مانند استاتین، گلوپیدگرول)مصرف می\u200Cکنند بهتر است استفاده نشود. چون ریسک خونریزی را زیاد می\u200Cکند. • بهتر است از این فراورده ها روزانه حداقل 4 کپسول (1 گرمی) برای کنترل تری گلیسرید بالا استفاده شود. • جهت تامین میزان موردنیاز امگا3، استفاده از کپسول های روغن کبد ماهی توصیه نمی شود. زیرا بدلیل محتوای بالای کلسترول و ویتامینهای A و D ، احتمال ایجاد مسمومیت با این کپسول وجود دارد");
            getD_list().add(d);
        }
        {

            d = new Drug();
            d.setDrug_code("");
            d.setDrug_name("داروهای کاهنده قند خون");
            d.setDrug_describe("اين دسته از دارو ها برای کنترل دیابت (بالا بودن قند خون) استفاده مي شود. براي كنترل ديابت یا باید انسولین بیشتری در لوزالمعده تولید شود یا سلول های بدن نسبت به انسولین حساس تر شوند و یا انسولین بصورت تزریقی وارد بدن شود. داروهای  خوراكي كاهنده قند خون که باعث تحریک لوزالمعده برای تولید مقادیر بیشتر انسولین می شوند  . شامل  :\n" +
                    "1-\tسولفونیل اوره ها و مگلیتینایدها : \n" +
                    "این داروها با تحریک سلول های بتای لوزالمعده بدن را وادار می کند تا انسولین بیشتری تولید کند بنابراین باعث پایین آمدن قند خون می شود.\n" +
                    " نمونه هایی از این گروه :\n" +
                    "1-\tگلی بن کلامید\t\t2- گلی کلازید\t\t3- رپاگلیناید\n" +
                    "عوارض جانبي این داروها می تواند شامل افت بیش از حد قند خون افزایش وزن و برخی عوارض قلبی باشد.\n" +
                    "•\tگلی بن کلامید در دیابت نوع ۲ کاربرد دارد و در دیابت نوع ۱ بی تاثیر است. \n" +
                    "•\t\tدر دوران بارداری و شیردهی از مصرف داروی گلی کلازید خودداری شود.\n" +
                    "•\tتوصیه می شود، داروی گلی بنکلامید و گلی کلازید با وعده های غذایی مصرف شود. اما داروی رپاگلیناید بهتر است قبل از غذا خورده شود.\n" +
                    "\n" +
                    "2-\tمتفورمين \n" +
                    "به نظر مي رسد كه اين دارو با کاهش تولید گلوکز از کبد و همچنین با افزایش حساسیت بافتهای بدن به انسولین باعث کاهش قند خون مي شود. اين دارو اغلب براي تمام افراد مبتلا به دیابت حتی کسانی که تحت درمان با انسولین هستند تجويز مي گردد. \n" +
                    "عوارض جانبی این داروها می تواند شامل، اختلالات گوارشي مثل تهوع و اسهال  باشد و بعضي از افراد بخاطر همين مشكلات از مصرف آن خودداري مي كنند. در حال حاضر نمونه آهسته رهش  این دارو که عوارض گوارشی کمتری دارد نیز به بازار دارویی ایران عرضه شده است.\n" +
                    "•\tاین دارو بهتر است با معده پر مصرف شود.\n" +
                    "•\tدر صورتی که دچار نارسایی کلیوی یا کبدی هستید حتما در مورد ادامه مصرف این دارو با پزشک خود مشورت کنید. نارسایی کلیوی یا کبدی از عوارض مصرف این دارو نمی باشد لیکن در افرادی که به هر علتی دچار چنین عوارضی هستند باید با احتیاط مصرف شود.\n" +
                    "•\tاین دارو در افرادی که دچار نارسایی قلب پیشرفته هستند نباید مصرف شود\n" +
                    "\n" +
                    "3-\tانسولين\n" +
                    "انسولین، هورمونی است که به منظور کنترل قند خونِ همۀ مبتلایان به دیابت نوع 1 و برخی از مبتلایان به دیابت نوع 2 و دیابت حاملگی استفاده می شود. دیابتی های نوع 1 به تزریق روزانۀ انسولین نیاز دارند، زیرا سلول های بتا در لوزالمعده این افراد تخریب شده و دیگر قادر به تولید انسولین و حفظ قند خون در حد نرمال نمی باشد. انسولین با هدایت قند داخل خون به درون سلول ها، انرژی بدن را تأمین می نماید. \n" +
                    "اینکه شما روزانه به چند واحد انسولین نیاز دارید و در چه ساعاتی باید تزریق خود را انجام دهید، به عوامل زیر بستگی دارد: نوع انسولین تجویز شده توسط پزشک، رژیم غذایی، فعالیت بدنی، دیگر داروهای مصرفی، مقدار انسولینی(در صورت وجود) که از سلول های بتای فعال باقی مانده در لوزالمعده ترشح می شود.\n" +
                    "نمونه های از این گروه:\n" +
                    "ویال های انسولین مانند:\n" +
                    "1-\t NPH \t\t2- رگولار \t\t3-  70/30\t\n" +
                    "قلم های انسولین مانند:\n" +
                    "1-\tلانتوس \t\t2- اپیدرا \t3- نوورپید \t4- نوومیکس \t\t5- لومیر \n" +
                    "عوارض جانبی این داروها شامل افت قند خون که خود را بصورت تعریق، لرزش و گرسنگی شدید و ضعف و بی حالی نشان می دهد. بزرگ شدن (هایپرتروفی) و یا آتروفی (تحلیل بافت) ناحیه ای از بدن که تزریقات انسولین زیادی دریافت کرده است و افزایش وزن می باشد\n" +
                    "4-\tتیازولیدین دیون ها:\n" +
                    " این دارو با افزایش حساسیت بافتهای بدن به انسولین عمل می کند.\n" +
                    "نمونه های از این گروه: پیوگلیتازون\n" +
                    "عوارض جانبی این داروها می تواند شامل افزایش وزن و ورم باشد .\n" +
                    "•\tاین دارو در افرادی که دچار نارسایی قلب پیشرفته هستند نباید مصرف شود.\n" +
                    "\n" +
                    "\n" +
                    "5-\tمهارکننده های دی پپتیدیل پپتیداز 4 : \n" +
                    "این دارو با مهار آنزیم DPP-4 باعث ترشح انسولین در یک مدت طولانی بعد از غذا می شود و در نتیجه قندخون پایین می آید .\n" +
                    "نمونه های از این گروه: سیتاگلیپتین\n" +
                    "عوارض جانبی این داروها می تواند شامل سردرد و کهیر باشد\n");
            getD_list().add(d);
        }
        this.setInstance(this);
    }

    public static druges getInstance() {
        if (instance != null)
            return instance;
        else return new druges();
    }

    public static void setInstance(druges instance) {
        druges.instance = instance;
    }

    public ArrayList<Drug> getD_list() {
        return d_list;
    }

    public void setD_list(ArrayList<Drug> d_list) {
        this.d_list = d_list;
    }
}
