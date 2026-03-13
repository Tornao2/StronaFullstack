package StronaSklepowa.Uczelnia.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import StronaSklepowa.Uczelnia.DTOs.CategoryDTO;
import StronaSklepowa.Uczelnia.Entities.Category;
import StronaSklepowa.Uczelnia.Repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getMainCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO updates) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategoria nie istnieje"));
        category.setName(updates.getName());
        category.setIconUrl(updates.getIconUrl());
        if (updates.getParentId() != null) {
            Category parent = categoryRepository.findById(updates.getParentId())
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono nowego rodzica"));
            category.setParent(parent);
        } else {
            category.setParent(null); 
        }
        return mapToDTO(categoryRepository.save(category));
    }

    private CategoryDTO mapToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIconUrl(category.getIconUrl());
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        if (category.getChildren() != null) {
            dto.setChildren(category.getChildren().stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Nie można usunąć – kategoria nie istnieje");
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setIconUrl(dto.getIconUrl());

        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Nie znaleziono rodzica"));
            category.setParent(parent);
        }

        return mapToDTO(categoryRepository.save(category));
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono kategorii o ID: " + id));
        return mapToDTO(category);
    }
}