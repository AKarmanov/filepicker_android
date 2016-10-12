package com.filepicker_android.filepicker.contextual;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author alexander karmanov on 2016-10-10.
 */

public class FilepickerFilter {

    public static final String SIZE = "size";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String NAME = "name";
    public static final String SORT_TYPE_ASC = "ascending";
    public static final String SORT_TYPE_DESC = "descending";

    public static final String FILTER_LAYOUT = "layoutFiler";
    public static final String FILTER_SORT = "sortFiler";

    public static Map<String, FilterSetting[]> sortOption;
    static {
        sortOption = new HashMap<>();
        sortOption.put(SIZE, new FilterSetting[]{
                new FilterSetting(SIZE, SORT_TYPE_ASC, false),
                new FilterSetting(SIZE, SORT_TYPE_DESC, false)
        });
        sortOption.put(TYPE, new FilterSetting[]{
                new FilterSetting(TYPE, SORT_TYPE_ASC, false),
                new FilterSetting(TYPE, SORT_TYPE_DESC, false)
        });
        sortOption.put(DATE, new FilterSetting[]{
                new FilterSetting(DATE, SORT_TYPE_ASC, false),
                new FilterSetting(DATE, SORT_TYPE_DESC, false)
        });
        sortOption.put(NAME, new FilterSetting[]{
                new FilterSetting(NAME, SORT_TYPE_ASC, false),
                new FilterSetting(NAME, SORT_TYPE_DESC, true)
        });
    }

    public static final String GRID = "grid";
    public static final String LIST = "list";
    public static final String LAYOUT_TYPE_SIMPLE = "layoutSimple";
    public static final String LAYOUT_TYPE_DETAILED = "layoutDetailed";


    public static Map<String, FilterSetting[]> layoutOption;
    static {
        layoutOption = new HashMap<>();
        layoutOption.put(GRID, new FilterSetting[]{
                new FilterSetting(GRID, LAYOUT_TYPE_DETAILED, false),
                new FilterSetting(GRID, LAYOUT_TYPE_SIMPLE, false)
        });
        layoutOption.put(LIST, new FilterSetting[]{
                new FilterSetting(LIST, LAYOUT_TYPE_DETAILED, true),
                new FilterSetting(LIST, LAYOUT_TYPE_SIMPLE, false)
        });
    }

    public static void setSortOption(String option, String sortType) {
        setOption(sortOption, option, sortType);
    }

    public static FilterSetting getSortOption() {
        return getOption(sortOption);
    }

    public static void setLayoutOption(String option, String sortType) {
        setOption(layoutOption, option, sortType);
    }

    public static FilterSetting getLayoutOption() {
        return getOption(layoutOption);
    }

    private static void setOption(Map<String, FilterSetting[]> map, String option, String sortType) {
        clearOptions(map);
        FilterSetting[] f = map.get(option);
        for (FilterSetting aF : f) {
            if (aF.getType().equals(sortType)) {
                aF.set();
            }
        }
    }

    private static FilterSetting getOption(Map<String, FilterSetting[]> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            FilterSetting[] f = map.get(key);
            for (FilterSetting aF : f) {
                if (aF.isSet()) {
                    return aF;
                }
            }
        }
        return null;
    }

    private static void clearOptions(Map<String, FilterSetting[]> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            FilterSetting[] f = map.get(key);
            for (FilterSetting aF : f) {
                if (aF.isSet()) {
                    aF.unset();
                }
            }
        }
    }

    public static class FilterSetting {
        private String option;
        private String type;
        private boolean set;

        public FilterSetting(String option, String type, boolean set) {
            this.option = option;
            this.type = type;
            this.set = set;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isSet() {
            return set;
        }

        public void unset() {
            set = false;
        }

        public void set() {
            set = true;
        }
    }

    public static void sort(List<FilepickerFile> files) {
        FilepickerFilter.FilterSetting setting = FilepickerFilter.getSortOption();
        switch(setting.getOption()) {
            case FilepickerFilter.SIZE :
                if (setting.getType().equals(FilepickerFilter.SORT_TYPE_DESC)) {
                    Collections.sort(files, FilepickerFilter.sizeSortInstance(FilepickerFilter.SORT_TYPE_DESC));
                }
                else {
                    Collections.sort(files, FilepickerFilter.sizeSortInstance(FilepickerFilter.SORT_TYPE_ASC));
                }

        }
    }

    private static SizeSort sizeSortInstance(String sortType) {
        return new SizeSort(sortType);
    }

    private static class SizeSort implements Comparator<FilepickerFile> {
        private String type;

        public SizeSort(String type) {
            this.type = type;
        }

        @Override
        public int compare(FilepickerFile o1, FilepickerFile o2) {
            switch (type) {
                case FilepickerFilter.SORT_TYPE_ASC :
                    return (int) (o1.getSize() - o2.getSize());
                case FilepickerFilter.SORT_TYPE_DESC :
                    return (int) (o2.getSize() - o1.getSize());
                default:
                    return (int) (o2.getSize() - o1.getSize());
            }
        }
    }
}
