package com.movies.util;

import java.io.FileInputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class MoviesUtil {

	TreeMap<Integer, String> m_info = new TreeMap<Integer, String>();// Binding
																		// Movie
																		// ID ::
																		// Movie
																		// Name
	TreeMap<Integer, Integer> m_count = new TreeMap<Integer, Integer>();// counting
																		// the
																		// view
																		// rate
	TreeMap<Integer, Integer> rat5 = new TreeMap<Integer, Integer>();// movies
																		// that
																		// are
																		// with
																		// 5
																		// rating
	TreeMap<Integer, Integer> rat4 = new TreeMap<Integer, Integer>();// movies
																		// tha
																		// are
																		// with
																		// 4
																		// rating
	TreeMap<Integer, Integer> rat3 = new TreeMap<Integer, Integer>();// movies
																		// that
																		// are
																		// with
																		// 3
																		// rating
	TreeMap<Integer, Integer> rat2 = new TreeMap<Integer, Integer>();// movies
																		// that
																		// are
																		// with
																		// 2
																		// rating
	TreeMap<Integer, Integer> rat1 = new TreeMap<Integer, Integer>();// movies
																		// that
																		// are
																		// with
																		// 1
																		// rating
	TreeMap<Integer, Integer> user_rat = new TreeMap<Integer, Integer>();// for
																			// calculate
																			// the
																			// critics
																			// count
	TreeMap<Integer, Double> rat_cal = new TreeMap<Integer, Double>();// count
																		// the
																		// most
																		// rated
																		// movies
	TreeSet<Integer> users_young = new TreeSet<Integer>();// users of age <20
	TreeSet<Integer> users_young_adult = new TreeSet<Integer>();// users of age
																// between 20-40
	TreeSet<Integer> users_adult = new TreeSet<Integer>();// users of age >40
	TreeMap<Integer, Integer> users_young_count = new TreeMap<Integer, Integer>();// top
																					// 10
																					// rated
																					// movies
																					// with
																					// no.of
																					// views
																					// in
																					// age
																					// <20
	TreeMap<Integer, Integer> users_young_adult_count = new TreeMap<Integer, Integer>();// top
																						// 10
																						// rated
																						// movies
																						// with
																						// no.of
																						// views
																						// in
																						// age
																						// 20-40
	TreeMap<Integer, Integer> users_adult_count = new TreeMap<Integer, Integer>();// top
																					// 10
																					// rated
																					// movies
																					// with
																					// no.of
																					// views
																					// in
																					// age
																					// >40

	// method for sorting the treeMap Data according to Values
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = -map.get(k1).compareTo(map.get(k2));
				if (compare == 0)
					return -1;
				else
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	// Counting the Movie view according to movie id && movie rating according
	// to rating rate
	public void MostViewed_Movies() throws Exception {
		Scanner in = new Scanner(new FileInputStream("ratings.txt"));
		int count = 0;
		while (in.hasNext()) {
			StringTokenizer st = new StringTokenizer(in.nextLine(), "::");
			if (st.hasMoreTokens()) {
				String k = st.nextToken();
				if (st.hasMoreTokens()) {
					String tar = st.nextToken();
					String rat = null;
					if (st.hasMoreTokens()) {
						rat = st.nextToken();
					}

					Integer i = new Integer(rat);
					if (i == 5)
						rat5.put(Integer.valueOf(tar), Integer.valueOf(rat5.get(Integer.valueOf(tar))) + 1);

					if (i == 4)
						rat4.put(Integer.valueOf(tar), Integer.valueOf(rat4.get(Integer.valueOf(tar))) + 1);

					if (i == 3)
						rat3.put(Integer.valueOf(tar), Integer.valueOf(rat3.get(Integer.valueOf(tar))) + 1);

					if (i == 2)
						rat2.put(Integer.valueOf(tar), Integer.valueOf(rat2.get(Integer.valueOf(tar))) + 1);

					if (i == 1) {
						rat1.put(Integer.valueOf(tar), Integer.valueOf(rat1.get(Integer.valueOf(tar))) + 1);

						// here calculating the critics
						user_rat.put(Integer.parseInt(k), ((Integer) user_rat.get(Integer.parseInt(k))) + 1);
					}

					if (m_info.containsKey(new Integer(tar))) {
						m_count.put(Integer.valueOf(tar), Integer.valueOf(m_count.get(Integer.valueOf(tar))) + 1);
					}

				}
			}
		}
	}

	// binding the movie id with Movie Name
	public void Bind_Id_Title() throws Exception {
		String ke = null;
		String valu = null;
		Scanner in = new Scanner(new FileInputStream("movies.txt"));
		while (in.hasNext()) {
			String line = in.nextLine();
			String tok[] = line.split("::");
			int count = 0;
			for (String s : tok) {
				if (count > 1)
					break;
				if (count == 0)
					ke = s;
				if (count == 1)
					valu = s;

				count++;
			}

			m_info.put(Integer.valueOf(ke), valu);
			m_count.put(Integer.valueOf(ke), 0);
			rat5.put(Integer.valueOf(ke), 0);
			rat4.put(Integer.valueOf(ke), 0);
			rat3.put(Integer.valueOf(ke), 0);
			rat2.put(Integer.valueOf(ke), 0);
			rat1.put(Integer.valueOf(ke), 0);
			rat_cal.put(Integer.valueOf(ke), 0.0);

		}
	}

	// Displaying the top 10 Viewed Movies
	public void displayRes() {
		Map sortedMap = this.sortByValues(m_count);
		Set set = sortedMap.entrySet();
		Iterator i = set.iterator();
		int count = 0;
		TreeSet<String> t = new TreeSet<String>();
		while (i.hasNext()) {
			if (count++ != 10) {
				Map.Entry me = (Map.Entry) i.next();
				t.add(m_info.get(me.getKey()));
			} else
				break;
		}

		System.out.println("1.Top 10 viewed Movies in Ascending Order");
		System.out.println("---------------------------------------------");
		System.out.println();
		Iterator ii = t.iterator();
		while (ii.hasNext()) {
			System.out.println(ii.next());
		}

	}

	// calculating and displaying the Most rated movies
	public void mostRated() {
		Set s = rat_cal.entrySet();
		Iterator i = s.iterator();
		while (i.hasNext()) {
			Map.Entry e = (Map.Entry) i.next();
			Double r5 = Double.valueOf(rat5.get(e.getKey()));
			Double r4 = Double.valueOf(rat4.get(e.getKey()));
			Double r3 = Double.valueOf(rat3.get(e.getKey()));
			Double r2 = Double.valueOf(rat2.get(e.getKey()));
			Double r1 = Double.valueOf(rat1.get(e.getKey()));
			Double sum = r5 + r4 + r3 + r2 + r1;
			if (sum >= 40) {
				Double avg = Double.valueOf((((r5 * 5) + (r4 * 4) + (r3 * 3) + (r2 * 2) + (r1 * 1)) / sum));
				Integer key = (Integer) e.getKey();
				rat_cal.put(key, avg);
			}

		}

		System.out.println();
		Map sortedMap1 = this.sortByValues(rat_cal);
		Set set1 = sortedMap1.entrySet();
		Iterator i1 = set1.iterator();
		int count1 = 0;
		System.out.println();
		System.out.println("2.Top 20 Rated Movies");
		System.out.println("-----------------------");
		System.out.println();
		while (i1.hasNext()) {
			if (count1++ != 20) {
				Map.Entry me1 = (Map.Entry) i1.next();

				System.out.println(m_info.get(me1.getKey()));

				// for 3 question purpose
				Integer key = ((Integer) me1.getKey());
				users_young_count.put(key, 0);
				users_young_adult_count.put(key, 0);
				users_adult_count.put(key, 0);
			} else
				break;
		}

	}

	// dividing users according to their age category
	public void users_Category() throws Exception {
		Scanner in = new Scanner(new FileInputStream("users.txt"));
		while (in.hasNext()) {
			String line = in.nextLine();
			String tokens[] = line.split("::");

			int user_id = Integer.parseInt(tokens[0]);
			int age_Group = Integer.parseInt(tokens[2]);

			user_rat.put(Integer.parseInt(tokens[0]), 0);

			if (age_Group < 20)
				users_young.add(user_id);
			else if (age_Group >= 20 && age_Group <= 40)
				users_young_adult.add(user_id);
			else if (age_Group > 40)
				users_adult.add(user_id);
		}

	}

	// counting most rated movies with no.of views according to age_Groups
	public void age_Groups() throws Exception {
		Scanner in = new Scanner(new FileInputStream("ratings.txt"));
		while (in.hasNext()) {
			String line = in.nextLine();
			String tokens[] = line.split("::");

			String uid = tokens[0];
			Integer m_id = Integer.parseInt(tokens[1]);
			if (users_young.contains(new Integer(uid))) {
				if (users_young_count.containsKey(m_id)) {
					users_young_count.put(m_id, Integer.valueOf(users_young_count.get(m_id)) + 1);
				}
			} else if (users_young_adult.contains(new Integer(uid))) {
				if (users_young_adult_count.containsKey(m_id)) {
					users_young_adult_count.put(m_id, Integer.valueOf(users_young_adult_count.get(m_id)) + 1);
				}
			} else if (users_adult.contains(new Integer(uid))) {
				if (users_adult_count.containsKey(m_id)) {
					users_adult_count.put(m_id, Integer.valueOf(users_adult_count.get(m_id)) + 1);
				}
			}

		}
	}

	// displaying 3rd question result
	public void age_Groups_Count_Display(TreeMap<Integer, Integer> tar) {
		Set s = tar.entrySet();
		Iterator i = s.iterator();
		while (i.hasNext()) {
			Map.Entry m = (Map.Entry) i.next();

			System.out.printf("%-75s %d", m_info.get((Integer) m.getKey()), m.getValue());
			System.out.println();
		}
		System.out.println();
	}

	// calling 3 question result
	public void age_Groups_Count() {
		System.out.println();
		System.out.println("3.Top 20 rated movies with no.of views in the following age groups");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println();
		System.out.println("For age groups of young(<20)");
		System.out.println("---------------------------------");
		System.out.println();
		this.age_Groups_Count_Display(users_young_count);

		System.out.println("For age groups of young&adult(20-40)");
		System.out.println("------------------------------------------");
		System.out.println();
		this.age_Groups_Count_Display(users_young_adult_count);
		System.out.println("For age groups of adult(>40)");
		System.out.println("--------------------------------");
		System.out.println();
		this.age_Groups_Count_Display(users_adult_count);
	}

	// Displaying the Top ten Critics
	public void users_rat_display() {
		System.out.println();
		System.out.println("4.top 10 critics ratings");
		System.out.println("-------------------------");
		System.out.println();
		Map sortedMap = this.sortByValues(user_rat);
		Set set = sortedMap.entrySet();
		Iterator i = set.iterator();
		int count = 0;

		while (i.hasNext()) {
			if (count++ != 10) {
				Map.Entry me = (Map.Entry) i.next();

				System.out.printf("%-5d %-5d", me.getKey(), me.getValue());
				System.out.println();

			} else
				break;
		}

	}

	public static void main(String[] args) throws Exception {
		MoviesUtil m = new MoviesUtil();
		m.Bind_Id_Title();
		m.users_Category();
		m.MostViewed_Movies();
		m.displayRes();
		m.mostRated();
		m.age_Groups();
		m.age_Groups_Count();
		m.users_rat_display();

	}

}
