package com.neuSep17.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.neuSep17.dto.Vehicle;

public class InventoryListService {

	public static ArrayList<Vehicle> readAndGetVehicles(File file) {
		ArrayList<Vehicle> res = new ArrayList<>();
		BufferedReader buf = null;
		try {
			buf = new BufferedReader(new FileReader(file));
			String curLine = buf.readLine();
			curLine = buf.readLine();
			while (curLine != null) {
				String[] arr = curLine.split("~");
				Vehicle v = new Vehicle(arr);
				res.add(v);
				curLine = buf.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	public static ArrayList<Vehicle> search(ArrayList<Vehicle> filter, ArrayList<Vehicle> list, JTextField txtSearch) {
		filter = new ArrayList<>();
		String str = txtSearch.getText();
		str = str.toUpperCase();

		for (Vehicle item : list) {
			HashSet<String> dic = new HashSet<>();
			dic.add(item.getID().toUpperCase());
			dic.add(item.getWebID().toUpperCase());
			dic.add(item.getCategory().toString().toUpperCase());
			dic.add(item.getMake().toUpperCase());
			dic.add(item.getModel().toUpperCase());
			dic.add(item.getTrim().toUpperCase());
			dic.add(item.getBodyType().toUpperCase());
			dic.add(String.valueOf(item.getYear()).toUpperCase());
			dic.add(item.getBattery().toUpperCase());
			dic.add(item.getEngine().toUpperCase());
			dic.add(item.getExteriorColor().toUpperCase());
			dic.add(item.getInteriorColor().toUpperCase());
			dic.add(item.getFuelType().toUpperCase());
			dic.add(item.getOptionalFeatures().toUpperCase());
			dic.add(item.getTransmission().toUpperCase());
			dic.add(item.getVin().toUpperCase());
			dic.add(item.getEntertainment().toUpperCase());
			boolean[] dp = new boolean[str.length() + 1];
			dp[0] = true;
			for (int i = 1; i <= str.length(); i++) {
				for (int j = 0; j < i; j++) {
					if (dp[j] && dic.contains(str.substring(j, i))) {
						dp[i] = true;
					}
				}
			}
			if (dp[str.length()]) {
				filter.add(item);
			}
		}
		return filter;
	}

	public static ArrayList<Vehicle> filter(ArrayList<Vehicle> filter, ArrayList<Vehicle> list, JTextField txtFilter) {
		filter = new ArrayList<>();
		String str = txtFilter.getText();
		str = str.toUpperCase();
		for (Vehicle item : list) {
			String pattern = item.getID() + item.getWebID() + item.getCategory().toString() + item.getYear() + item.getMake() + item.getModel()
					+ item.getTrim() + item.getBodyType() + item.getPrice() + item.getVin() + item.getEntertainment() + item.getInteriorColor()
					+ item.getExteriorColor() + item.getFuelType() + item.getEngine() + item.getTransmission() + item.getBattery() + item.getOptionalFeatures();
			if (pattern.toUpperCase().contains(str)) {
				filter.add(item);
			}
		}
		return filter;
	}

	public static void fillTable(ArrayList<Vehicle> list, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);

		for (Vehicle vehicle : list) {
			String[] arr = new String[19];
			arr[0] = vehicle.getID();
			arr[1] = vehicle.getWebID();
			arr[2] = vehicle.getCategory().toString();
			arr[3] = String.valueOf(vehicle.getYear());
			arr[4] = vehicle.getMake();
			arr[5] = vehicle.getModel();
			arr[6] = vehicle.getTrim();
			arr[7] = vehicle.getBodyType();
			arr[8] = String.valueOf(vehicle.getPrice());
			arr[9] = "<html><img src=\""+vehicle.getPhotoURL().toString()+"\"/></html>";
			arr[10] = vehicle.getVin();
			arr[11] = vehicle.getEntertainment();
			arr[12] = vehicle.getInteriorColor();
			arr[13] = vehicle.getExteriorColor();
			arr[14] = vehicle.getFuelType();
			arr[15] = vehicle.getEngine();
			arr[16] = vehicle.getTransmission();
			arr[17] = vehicle.getBattery();
			arr[18] = vehicle.getOptionalFeatures();

			tableModel.addRow(arr);
		}

		table.invalidate();
	}

	public static void sortById(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getID().compareTo(o2.getID());
				} else {
					return o2.getID().compareTo(o1.getID());
				}
			}
		});
	}

	public static void sortByWebId(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getWebID().compareTo(o2.getWebID());
				} else {
					return o2.getWebID().compareTo(o1.getWebID());
				}
			}
		});
	}

	public static void sortByCategory(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getCategory().compareTo(o2.getCategory());
				} else {
					return o2.getCategory().compareTo(o1.getCategory());
				}
			}
		});
	}

	public static void sortByYear(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getYear() - o2.getYear();
				} else {
					return o2.getYear() - o1.getYear();
				}
			}
		});
	}

	public static void sortByPrice(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return (int) (o1.getPrice() - o2.getPrice());
				} else {
					return (int) (o2.getPrice() - o1.getPrice());
				}
			}
		});
	}
	
	public static void sortByModel(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getModel().compareTo(o2.getModel());
				} else {
					return o2.getModel().compareTo(o1.getModel());
				}
			}
		});
	}
	
	public static void sortByExteriorColor(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getExteriorColor().compareTo(o2.getExteriorColor());
                } else {
                    return o2.getExteriorColor().compareTo(o1.getExteriorColor());
                }
            }
        });
    }
	
	public static void sortByInteriorColor(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getInteriorColor().compareTo(o2.getInteriorColor());
                } else {
                    return o2.getInteriorColor().compareTo(o1.getInteriorColor());
                }
            }
        });
    }
	
	public static void sortByTransmission(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getTransmission().compareTo(o2.getTransmission());
                } else {
                    return o2.getTransmission().compareTo(o1.getTransmission());
                }
            }
        });
    }
	
	public static void sortByEngine(ArrayList<Vehicle> list, boolean isAscend) {
        Collections.sort(list, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if (isAscend) {
                    return o1.getEngine().compareTo(o2.getEngine());
                } else {
                    return o2.getEngine().compareTo(o1.getEngine());
                }
            }
        });
    }

	public static void sortByType(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getBodyType().compareTo(o2.getBodyType());
				} else {
					return o2.getBodyType().compareTo(o1.getBodyType());
				}
			}
		});
	}

	public static void sortByMake(ArrayList<Vehicle> list, boolean isAscend) {
		Collections.sort(list, new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if (isAscend) {
					return o1.getMake().compareTo(o2.getMake());
				} else {
					return o2.getMake().compareTo(o1.getMake());
				}
			}
		});
	}
}
