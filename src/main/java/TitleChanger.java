import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *  kiedy cos jest na liscie i usuwam cos innego, to nie moge dodac tego co przed chwila usunalem(modelListy.get(i).getPath jest rowny usunietemu elementowi)
 * wyszukiwanie numeru odcinka po nazwie nie działa
 * wyszukiwanie nazwy odcinka po numerze działa połowicznie(czyta tylko jedno słowo tytułu)
 * <p>
 * wersja okienkowa programu do unifikacyjnej zmiany nazwy odcinkow serialu.
 * w okienku bedzie lista dodanych plikow i ich ewentualne nazwy po zmianie.
 * istnieje mozliwosc samodzielnej edycji poszczególnych nazw
 * <p>
 */
public class TitleChanger extends JFrame {

    private RysowaniePoPanelu imagePanel = new RysowaniePoPanelu();

    public TitleChanger() {

        this.setTitle("Title Changer");
        this.setBounds(250, 250, 500, 570);
        this.setJMenuBar(jMenuBar);

        JMenu menuFile = jMenuBar.add(new JMenu("Plik"));


        Action addAction = new Action("Dodaj", "Dodaj nowy wpis do archiwum", "ctrl D", new ImageIcon("dodaj.png"));

        Action deleteAction = new Action("Usuń", "Usuń zaznaczony/zaznaczone wpisy z archiwum", "ctrl U", new ImageIcon("usun.png"));

        Action changeNamesAction = new Action("Zmień nazwy", "zmienia nazwy  z tych na liście 1 na te z listy 2", "ctrl Z");

        menuFile.add(addAction);
        menuFile.add(deleteAction);
        menuFile.add(changeNamesAction);

        bAdd = new JButton(addAction);
        bDelete = new JButton(deleteAction);
        bEditManual = new JButton("Edytuj ręcznie", new ImageIcon("dlugopis.png"));
        bEditMainName = new JButton("Zmień nazwę domyślną");

        bEditMainName.addActionListener(new ActionListener() {
            @Override


            public void actionPerformed(ActionEvent e) {

                String newMainTitle = (String) JOptionPane.showInputDialog(
                        TitleChanger.getFrames()[0],
                        "Wpisz nowy główny tytuł",
                        "Edytor Głównego Tytułu",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        mainTitle
                );
                mainTitle=newMainTitle;
                if (newMainTitle != null && newMainTitle.length() > 0) {

                    JOptionPane.showMessageDialog(null, "Zmieniono nazwe");

                } else

                    JOptionPane.showMessageDialog(null, "Nie zmieniono nazwy");

            }
        });
        bEditManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] tmp = list2.getSelectedIndices();
                if (tmp.length != 0) {
                    String result = (String) JOptionPane.showInputDialog(
                            TitleChanger.getFrames()[0],
                            "Wpisz nową nazwę",
                            "Edytor Nazwy",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            listModel2.getElementAt(tmp[0])
                    );
                    if (result != null && result.length() > 0) {
                        listModel2.set(tmp[0], result);
                        JOptionPane.showMessageDialog(null, "Zmieniono nazwe");

                    } else

                        JOptionPane.showMessageDialog(null, "Nie zmieniono nazwy");
                } else {

                    JOptionPane.showMessageDialog(null, "Dodaj jakiś plik i zaznacz go w liscie 2");

                }
            }
        });
        bChange = new JButton(changeNamesAction);

        JScrollPane jScrollPane = new JScrollPane(list);
        JScrollPane jScrollPane2 = new JScrollPane(list2);

        imagePanel.setBorder(BorderFactory.createEtchedBorder());
        GroupLayout layout = new GroupLayout(this.getContentPane());

        layout.setHorizontalGroup(
                layout.createParallelGroup()


                        .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane, 50, 150, 300)

                        .addComponent(jScrollPane2, 50, 150,300)

                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(bAdd)
                                        .addComponent(bDelete)
                                        .addComponent(bEditManual)
                                        .addComponent(bChange)
                                        .addComponent(bEditMainName)
                        )
                        )
                        .addComponent(imagePanel)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()


                        .addGroup(layout.createParallelGroup()

                        .addComponent(jScrollPane, 50, 100, 200)
                        .addComponent(jScrollPane2, 50, 100,200)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(bAdd)
                                .addComponent(bDelete)
                                .addGap(50,50,50)
                                .addComponent(bEditManual)
                                .addComponent(bChange)
                                .addComponent(bEditMainName))
                        )
                        .addComponent(imagePanel)
        );
        this.getContentPane().setLayout(layout);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private class RysowaniePoPanelu extends JPanel {
        public RysowaniePoPanelu() {
            super();

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(new ImageIcon("logo.png").getImage(), 0, 0, null);

    }
}


    private DefaultListModel listModel = new DefaultListModel() {
        @Override
        public void addElement(Object obj) {
            list.add(obj);
            super.addElement(((File) obj).getName());
        }

        @Override
        public Object get(int index) {
            return list.get(index);
        }

        ArrayList list = new ArrayList();
    };
    private DefaultListModel listModel2 = new DefaultListModel() {
        @Override
        public void addElement(Object obj) {
            list2.add(obj);
            super.addElement((obj));
        }

        @Override
        public Object get(int index) {
            return list2.get(index);
        }

        ArrayList list2 = new ArrayList();

    };
    private JList list = new JList(listModel);
    private JList list2 = new JList(listModel2);
    private JButton bAdd;
    private JButton bEditMainName;
    private JButton bDelete;
    private JButton bChange;
    private JButton bEditManual;
    private JFileChooser jFileChooser = new JFileChooser();
    private JMenuBar jMenuBar = new JMenuBar();
    private final String[] TEXT_EXTENDS = new String[]{".txt", ".xml"};
    private final String[] MOVIES_EXTENDS = getMOVIES_EXTENDS("video.txt");
    public String mainTitle="Świat według Kiepskich ";

    private String[] getMOVIES_EXTENDS(String nazwaPliku) {

        String[] extNames = null;
        BufferedReader bRead = null;
        try {
            bRead = new BufferedReader(new FileReader(nazwaPliku));
            int i = 0, dlugoscTablicy = 0;
            while (bRead.readLine() != null) {
                dlugoscTablicy++;
            }
            extNames = new String[dlugoscTablicy];
            String linia;
            bRead.close();
            BufferedReader bread = new BufferedReader(new FileReader(nazwaPliku));

            while ((linia = bread.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(linia);
                token.nextToken();
                extNames[i] = token.nextToken().toLowerCase();
                i++;
            }


            if (bread != null)
                bread.close();
        } catch (IOException e) {            System.out.println(e.getMessage());
        } finally {

            return extNames;
        }

    }
    public static void main(String[] args) {

        new TitleChanger().setVisible(true);
    }

    /**
     * Pierwsza klasa wewnętrzna programu TitleChanger
     */

    public class ExtendsFilter extends FileFilter
    {
        private String opis;
        private String[] fileExtend;

        public ExtendsFilter(String opis, String[] fileExtend)
        {
            this.opis = opis;
            this.fileExtend = fileExtend;
        }

        @Override
        public boolean accept(File f)
        {
            for (int i = 0; i < this.fileExtend.length; i++)
                if (f.getName().toLowerCase().endsWith(this.fileExtend[i]) || f.isDirectory())
                    return true;

            return false;
        }

        @Override
        public String getDescription()
        {
            return opis;
        }


    }

    /**
     * druga klasa wewnętrzna
     */
    public class Action extends AbstractAction {

        public Action(String name, String description, String shortcut) {
            this.putValue(javax.swing.Action.NAME, name);
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, description);
            this.putValue(javax.swing.Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(shortcut));
        }

        public Action(String name, String description, String shortcut, Icon icon) {
            this(name, description, shortcut);
            this.putValue(javax.swing.Action.SMALL_ICON, icon);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Dodaj"))
                addRecordsToArchive();
            else if (e.getActionCommand().equals("Usuń"))
                deleteRecordsFromList();
            else if (e.getActionCommand().equals("Zmień nazwy"))
                saveChangedName();

        }

        private void deleteRecordsFromList() {
            int[] tmp = list.getSelectedIndices();
            int suma = 0;
            for (int i = 0; i < tmp.length; i++) {

                listModel.remove(tmp[i] - i);
                listModel2.remove(tmp[i] - i);
                suma++;
            }
            JOptionPane.showMessageDialog(null, "Usunieto " + suma + " wpisów","Info",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("delete.png"));
        }

        private void saveChangedName() {
            int[] tab = list.getSelectedIndices();
            int suma = 0;
            for (int i = 0; i < tab.length; i++) {
                FileNameChanger z = new FileNameChanger();
                z.changeName((String) listModel.getElementAt(i), (String) listModel2.getElementAt(i));
                suma++;
            }
            deleteRecordsFromList();
            JOptionPane.showMessageDialog(null, "Zmieniono nazwę " + suma + " wpisów","Info",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("done.png"));
        }

        private void addRecordsToArchive() {

            jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.setMultiSelectionEnabled(true);
            jFileChooser.setAcceptAllFileFilterUsed(true);
            jFileChooser.addChoosableFileFilter(new ExtendsFilter("Pola tekstowe", TEXT_EXTENDS));
            jFileChooser.addChoosableFileFilter(new ExtendsFilter("Pliki video", MOVIES_EXTENDS));
            jFileChooser.addChoosableFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "Katalogi";
                }
            });



            int tmp = jFileChooser.showDialog(rootPane, "Dodaj do listy");

            if (tmp == JFileChooser.APPROVE_OPTION) {
                File[] paths = jFileChooser.getSelectedFiles();

                for (File file : paths)
                    if (!isRecordDuplicated(file.getPath())) {
                        listModel.addElement(file);

                        FileNameChanger fileNameChanger = new FileNameChanger();

                        listModel2.addElement(fileNameChanger.showNewName(file.getName()));
                    }
            }

        }

        private boolean isRecordDuplicated(String testedRecord) {
            for (int i = 0; i < listModel.getSize(); i++)
                if (((File) listModel.get(i)).getPath().equals(testedRecord))
                    return true;
            return false;
        }

        /**
         * trzecia klasa wewnętrzna klasy TitleChanger
         */
        private class FileNameChanger {
            String extension = "";
            String title;
            String mainTitle=TitleChanger.this.mainTitle;
            int episodeNumber;
            String nowaNazwa;

            /**
             * tworzy plik z nową nazwą
             *
             * @return zwraca tenże plik
             */
            public String createNewName() {
                if (this.episodeNumber != 0) {
                    this.nowaNazwa = mainTitle + this.episodeNumber + " " + this.title + this.extension;
                    return this.nowaNazwa;
                }
                return this.nowaNazwa = mainTitle + " " + this.title + this.extension;
            }

            /**
             * sprawdza nazwę pliku pod kątem informacji o tytule i nr odcinka
             *
             * @param file plik
             * @return -jeśli jest domniemany tutuł, to zwraca 0
             * -jeśli jest domniemany nr odcinka, to zwraca 1
             */
            public int coMamWNazwie(File file) {
                for (int i = 0; i < file.getName().length(); i++) {
                    if (Character.isDigit(file.getName().charAt(i)))
                        return 1;
                }
                return 0;
            }

            /**
             * ustawia rozszerzenie pliku, czyli co znajduje sie po ostaniej kropce(z kropka wlacznie)
             *
             * @param file
             */
            public void setExtension(File file) {
                for (int i = file.getName().length() - 1; i >= 0; i--) {
                    if (file.getName().charAt(i) == '.') {
                        for (int j = i; j < file.getName().length(); j++) {
                            this.extension += file.getName().charAt(j);
                        }
                        break;
                    }
                }

            }

            /**
             * ustawia nr odcinka z pliku
             *
             * @param file
             */
            public void setEpisodeNumber(File file) {
                String episodeNumber = "";
                for (int i = 0; i < file.getName().length(); i++)
                    if (Character.isDigit(file.getName().charAt(i)))
                        episodeNumber += file.getName().charAt(i);
                if (episodeNumber.equals(""))
                    episodeNumber = "0";
                this.episodeNumber = Integer.parseInt(episodeNumber);
            }

            /**
             * bierze starą nazwę i nazywa ją tytułem
             *
             * @param file plik
             */
            public void setTitle(File file) {
                String name = "";
                for (int i = file.getName().length() - 1; i >= 0; i--) {
                    if (file.getName().charAt(i) == '.') {
                        for (int j = 0; j < i; j++) {
                            name += file.getName().charAt(j);
                        }
                        break;
                    }
                }
                this.title = name;
            }

            public void dopiszTytulzBazy(int nrOdcinka) {
                try {

                    RandomAccessFile raf = new RandomAccessFile("Lista odcinków kepscy.txt", "r");
                    String linia;
                    while ((linia = raf.readLine()) != null) {
                        StringTokenizer token = new StringTokenizer(linia);
                        String tempString = token.nextToken();
                        boolean czyLiczba = true;
                        for (int i = 0; i < tempString.length(); i++) {
                            if (!Character.isDigit(tempString.charAt(i))) {
                                czyLiczba = false;
                                break;
                            }
                        }
                        if (czyLiczba && Integer.parseInt(tempString) == nrOdcinka) {
                            this.title = token.nextToken();

                            break;
                        }
                    }
                    raf.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            public String showNewName(String filename) {
                File file = new File(filename);

                setExtension(file);

                if (coMamWNazwie(file) == 1) {

                    setEpisodeNumber(file);
                    dopiszTytulzBazy(episodeNumber);

                } else {
                    setTitle(file);
//            dopiszNrOdcinkazBazy(tytul);
                }
                return createNewName();
            }

            public void changeName(String filename, String filename2) {
                File file = new File(filename);
                File newFile = new File(filename2);
                file.renameTo(newFile);
            }

        }

        public boolean dopiszNrOdcinkazBazy(String tytul) {

            byte[] tytBajt = " \u0012Kiepski czarnowidz".getBytes();
            System.out.println("tytul:");
            System.out.println(tytul);
            System.out.println("dlugosc tablicy bajtow:");
            System.out.println(tytBajt.length);
            try {
                BufferedInputStream bIn = new BufferedInputStream(new FileInputStream("Lista odcinków kiepscy.txt"));
                BufferedOutputStream bOut = new BufferedOutputStream(new FileOutputStream("Lista odcinków kiepscy.bin"));
                RandomAccessFile raf = new RandomAccessFile("Lista odcinków kiepscy.bin", "rw");
                int bajt, i = 0;
                char baaa;
                int c;
                int BUFOR = 1024;
                byte[] tempB = new byte[BUFOR];
                while ((c = bIn.read()) != -1) {
                    raf.writeByte(c);
                }
                bOut.close();
                bIn.close();
                raf.seek(0);

                while ((bajt = raf.read()) != -1) {
                    i = 0;
                    while (i < tytBajt.length && bajt == tytBajt[i]) {

//                    System.out.println("file pointer");
//                    System.out.println(raf.getFilePointer());
//                    raf.seek(raf.getFilePointer()-18);
//                    System.out.println("file pointer");
//                    System.out.println(raf.getFilePointer());
//                    System.out.println("raf read:");
//                    System.out.println(raf.read());
//                    System.out.println("nr odc.");
//                    System.out.println(this.nrOdcinka);
                        i++;

                        bajt = raf.read();

                    }
                    System.out.println(i);
                    if (i + 1 == tytBajt.length) {                        //FIXME: nie wiem czemu, ale tutaj nie dziala
                        System.out.println("raf.getFilePointer():");
                        System.out.println(raf.getFilePointer());
                        raf.seek(raf.getFilePointer());
                        System.out.println("i");
                        System.out.println(i);
                        System.out.println("f read");


                        for (int k = 0; k < 100; k++)

                            System.out.print((char) raf.read());

                        System.out.println();
                        raf.close();

                        return true;
                    }

                }

                raf.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                return false;
            }
        }
    }
}






